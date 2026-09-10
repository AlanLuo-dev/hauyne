package com.luoyx.hauyne.admin.sys.service.impl;

import com.luoyx.hauyne.admin.amqp.producer.AuditProducer;
import com.luoyx.hauyne.admin.api.sys.audit.RoleAuthorityAuditDTO;
import com.luoyx.hauyne.admin.api.sys.enums.YesNoEnum;
import com.luoyx.hauyne.admin.sys.converter.AuthorityConverter;
import com.luoyx.hauyne.admin.sys.converter.RoleConverter;
import com.luoyx.hauyne.admin.sys.entity.Authority;
import com.luoyx.hauyne.admin.sys.entity.Role;
import com.luoyx.hauyne.admin.sys.entity.RoleAuthority;
import com.luoyx.hauyne.admin.sys.mapper.RoleMapper;
import com.luoyx.hauyne.admin.sys.request.RoleAuthoritiesUpdateDTO;
import com.luoyx.hauyne.admin.sys.request.RoleCreateDTO;
import com.luoyx.hauyne.admin.sys.request.RoleUpdateDTO;
import com.luoyx.hauyne.admin.sys.response.RoleDropdownVO;
import com.luoyx.hauyne.admin.sys.service.AuthorityService;
import com.luoyx.hauyne.admin.sys.service.RoleAuthorityService;
import com.luoyx.hauyne.admin.sys.service.RoleService;
import com.luoyx.hauyne.admin.sys.service.UserRoleService;
import com.luoyx.hauyne.mybatisplus.service.impl.BaseServiceImpl;
import com.luoyx.hauyne.web.exception.ValidateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author 罗英雄
 * @since 2022-07-30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleAuthorityService roleAuthorityService;
    private final AuthorityService authorityService;
    private final UserRoleService userRoleService;

    private final AuthorityConverter authorityConverter;

    private final RoleConverter roleConverter;

    private final AuditProducer auditProducer;

    /**
     * 校验角色编码可用性
     *
     * @param excludeRoleId 要排除的角色Id（编辑角色的场景）
     * @param roleCode      角色编码
     * @return true 表示角色编码可用，false 表示角色编码已存在
     */
    @Override
    public boolean isRoleCodeUnique(Long excludeRoleId, String roleCode) {
        return baseMapper.selectOneByRoleCode(excludeRoleId, roleCode) == null;
    }

    /**
     * 校验角色名称可用性
     *
     * @param excludeRoleId 要排除的角色Id（编辑角色的场景）
     * @param roleName      角色名称
     * @return true 表示角色名称可用，false 表示角色名称已存在
     */
    @Override
    public boolean isRoleNameUnique(Long excludeRoleId, String roleName) {
        return baseMapper.selectOneByRoleName(excludeRoleId, roleName) == null;
    }

    /**
     * 表单参数校验
     *
     * @param role 角色表单数据
     */
    @Override
    public void checkRoleFormData(Role role) {
        Long id = role.getId();
        String roleCode = role.getRoleCode();
        if (!isRoleCodeUnique(id, roleCode)) {
            throw new ValidateException("角色编码【" + roleCode + "】已存在，请勿重复添加");
        }
        String roleName = role.getRoleName();
        if (!isRoleNameUnique(id, roleName)) {
            throw new ValidateException("角色名称【" + roleName + "】已存在，请勿重复添加");
        }
    }

    /**
     * 新增角色
     *
     * @param roleCreateDTO 新增角色表单数据
     * @return 新增的角色
     */
    @Override
    @Transactional
    public Role create(RoleCreateDTO roleCreateDTO) {
        Role role = roleConverter.toRole(roleCreateDTO);
        checkRoleFormData(role);
        baseMapper.insert(role);
        auditProducer.sendToAudit(roleConverter.toRoleAuditDTO(role));

        return role;
    }

    /**
     * 修改角色
     *
     * @param roleUpdateDTO 修改角色表单数据
     */
    @Override
    @Transactional
    public void update(RoleUpdateDTO roleUpdateDTO) {
        Role existRole = baseMapper.selectById(roleUpdateDTO.getId());
        if (Objects.isNull(existRole)) {
            throw new ValidateException("角色不存在");
        }
        if (YesNoEnum.YES.equals(existRole.getBuiltin())) {
            throw new ValidateException("【" + existRole.getRoleName() + "】是系统内置角色，不允许修改");
        }
        Role role = roleConverter.toRole(roleUpdateDTO);
        checkRoleFormData(role);

        baseMapper.updateById(role);
        auditProducer.sendToAudit(roleConverter.toRoleAuditDTO(role));
    }

    /**
     * 批量删除角色
     *
     * @param ids 角色id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(Collection<Long> ids) {

        // 去除重复角色id
        ids = new HashSet<>(ids);
        List<Role> existRoleList = baseMapper.selectByIds(ids);
        if (CollectionUtils.isEmpty(existRoleList)) {
            throw new ValidateException("角色不存在");
        }
        if (existRoleList.size() != ids.size()) {
            throw new ValidateException("部分角色不存在，请刷新后重试");
        }
        for (Role existRole : existRoleList) {
            if (YesNoEnum.YES.equals(existRole.getBuiltin())) {
                throw new ValidateException("【" + existRole.getRoleName() + "】是系统内置角色，不允许删除");
            }
        }
        String roleName = userRoleService.countUserRoleByRoleIds(ids);
        if (StringUtils.isNotBlank(roleName)) {
            throw new ValidateException("角色【" + roleName + "】已分配给用户，请先解除关联后删除");
        }
        List<Role> roles = baseMapper.selectByIds(ids);
        for (Role role : roles) {
            auditProducer.sendToShadowDelete(roleConverter.toRoleAuditDTO(role));
        }
        baseMapper.deleteByIds(ids);
        roleAuthorityService.deleteByRoleIds(ids);
    }

    /**
     * 更新角色的权限资源（先删后增）
     *
     * @param roleId                   角色id
     * @param roleAuthoritiesUpdateDTO 参数DTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRoleAuthorities(Long roleId, RoleAuthoritiesUpdateDTO roleAuthoritiesUpdateDTO) {
        Role role = checkRoleExists(roleId);
        if (YesNoEnum.YES.equals(role.getBuiltin())) {
            throw new ValidateException("【" + role.getRoleName() + "】是系统内置角色，不允许修改权限");
        }
        final List<Long> authorityIds = roleAuthoritiesUpdateDTO.getAuthorityIds();
        final Set<Long> authorityIdSet = new LinkedHashSet<>(authorityIds); // 去重
        if (authorityIdSet.size() != authorityIds.size()) {
            throw new ValidateException("提交的权限菜单重复，请检查后重试");
        }
        Map<Long, Authority> authorityMap = Collections.emptyMap();
        if (CollectionUtils.isNotEmpty(authorityIdSet)) {
            authorityMap = authorityService.checkAuthorityIds(authorityIdSet);
            if (authorityMap.size() != authorityIdSet.size()) {
                throw new ValidateException("部分权限菜单不存在，请刷新后重试");
            }
        }

        // 先删除角色的权限资源
        roleAuthorityService.deleteByRoleIds(Collections.singletonList(roleId));

        RoleAuthorityAuditDTO roleAuthorityAuditDTO = new RoleAuthorityAuditDTO();
        roleAuthorityAuditDTO.setRoleId(roleId);

        // 再新增角色的权限资源
        if (CollectionUtils.isNotEmpty(authorityIdSet)) {
            Collection<RoleAuthority> roleAuthorities = authorityIdSet.stream()
                    .map(item -> {
                        RoleAuthority roleAuthority = new RoleAuthority();
                        roleAuthority.setRoleId(roleId);
                        roleAuthority.setAuthorityId(item);

                        return roleAuthority;
                    })
                    .collect(Collectors.toList());
            roleAuthorityService.saveBatch(roleAuthorities);

            List<String> authorityNames = authorityMap.values().stream()
                    .map(Authority::getAuthorityName)
                    .collect(Collectors.toList());
            roleAuthorityAuditDTO.setAuthorityNames(authorityNames);
        }

        // 更新角色权限菜单父子节点选中状态是否联动
        Role updateRole = new Role();
        updateRole.setId(roleId);
        updateRole.setAuthorityCheckLinkage(roleAuthoritiesUpdateDTO.getAuthorityCheckLinkage());
        baseMapper.updateById(updateRole);

        auditProducer.sendToAudit(roleAuthorityAuditDTO);
    }

    /**
     * 查询角色已有的权限（只查询叶子节点，前端Ng-Zorro的复选框树会根据子节点的选中情况决定的父节点是否半选中）
     *
     * @param roleId 角色id
     * @return 权限资源id集合(叶子节点)
     */
    @Override
    public List<String> selectLeafNodeAuthorityIdsByRoleId(Long roleId) {
        return baseMapper.selectLeafNodeAuthorityIdsByRoleId(roleId);
    }

    @Override
    public List<Role> selectAll() {
        return baseMapper.selectAll();
    }

    /**
     * 查询角色下拉框数据列表
     *
     * @return 角色下拉框数据列表
     */
    @Override
    public List<RoleDropdownVO> selectDropdown() {
        return baseMapper.selectDropdown();
    }

    /**
     * 校验角色是否存在
     *
     * @param roleId 角色id
     * @return 角色信息
     */
    @Override
    public Role checkRoleExists(Long roleId) {
        Role role = baseMapper.selectById(roleId);
        if (null == role) {
            throw new ValidateException("角色不存在");
        }

        return role;
    }

    /**
     * 校验角色是否存在
     *
     * @param roleIds 角色id集合
     * @return 角色信息列表
     */
    @Override
    public List<Role> checkRolesExist(Set<Long> roleIds) {
        List<Role> roles = baseMapper.selectBatchIds(roleIds);
        Map<Long, Role> roleMap = roles.stream().collect(Collectors.toMap(Role::getId, item -> item));
        for (Long roleId : roleIds) {
            if (!roleMap.containsKey(roleId)) {
                throw new ValidateException("角色id" + roleId + "不存在");
            }
        }
        return roles;
    }
}
