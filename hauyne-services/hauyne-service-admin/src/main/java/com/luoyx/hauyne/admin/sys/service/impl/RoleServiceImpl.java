package com.luoyx.hauyne.admin.sys.service.impl;

import com.luoyx.hauyne.admin.amqp.producer.AuditProducer;
import com.luoyx.hauyne.admin.api.sys.audit.RoleAuthorityAuditDTO;
import com.luoyx.hauyne.admin.sys.converter.AuthorityConverter;
import com.luoyx.hauyne.admin.sys.converter.RoleConverter;
import com.luoyx.hauyne.admin.sys.entity.Authority;
import com.luoyx.hauyne.admin.sys.entity.Role;
import com.luoyx.hauyne.admin.sys.entity.RoleAuthority;
import com.luoyx.hauyne.admin.sys.mapper.RoleMapper;
import com.luoyx.hauyne.admin.sys.query.RoleCodeUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.RoleNameUniqueCheckQuery;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
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
     * @param query 角色编码唯一性校验查询参数
     * @return true 表示角色编码可用，false 表示角色编码已存在
     */
    @Override
    public boolean checkRoleCodeUnique(RoleCodeUniqueCheckQuery query) {
        return baseMapper.countByRoleCode(query) == 0L;
    }

    /**
     * 校验角色名称可用性
     *
     * @param query 角色名称唯一性校验查询参数
     * @return true 表示角色名称可用，false 表示角色名称已存在
     */
    @Override
    public boolean checkRoleNameUnique(RoleNameUniqueCheckQuery query) {
        return baseMapper.countByRoleName(query) == 0L;
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
        if (!checkRoleCodeUnique(new RoleCodeUniqueCheckQuery(id, roleCode))) {
            throw new ValidateException("角色编码【" + roleCode + "】已存在，请勿重复添加");
        }
        String roleName = role.getRoleName();
        if (!checkRoleNameUnique(new RoleNameUniqueCheckQuery(id, roleName))) {
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
    public void update(RoleUpdateDTO roleUpdateDTO) {
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
    public void deleteByIds(List<Long> ids) {
        String roleName = userRoleService.countUserRoleByRoleIds(ids);
        if (StringUtils.isNotBlank(roleName)) {
            throw new ValidateException("角色【" + roleName + "】已分配给用户，请先解除关联后删除");
        }
        List<Role> roles = baseMapper.selectBatchIds(ids);
        for (Role role : roles) {
            auditProducer.sendToShadowDelete(roleConverter.toRoleAuditDTO(role));
        }
        baseMapper.deleteBatchIds(ids);
        roleAuthorityService.deleteByRoleIds(ids);
    }

    /**
     * 更新角色的权限资源（先删后增）
     *
     * @param roleId       角色id
     * @param authorityIds 权限资源id数组
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRoleAuthorities(Long roleId, List<Long> authorityIds) {
        checkRoleExists(roleId);
        Set<Long> authorityIdSet = new LinkedHashSet<>(authorityIds); // 去重
        Map<Long, Authority> authorityMap = Collections.emptyMap();
        if (CollectionUtils.isNotEmpty(authorityIdSet)) {
            authorityMap = authorityService.checkAuthorityIds(authorityIdSet);
        }

        // 先删除角色的权限资源
        roleAuthorityService.deleteByRoleIds(Collections.singletonList(roleId));

        RoleAuthorityAuditDTO  roleAuthorityAuditDTO = new RoleAuthorityAuditDTO();
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
