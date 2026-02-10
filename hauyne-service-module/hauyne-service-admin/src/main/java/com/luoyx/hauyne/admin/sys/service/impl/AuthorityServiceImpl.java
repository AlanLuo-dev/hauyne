package com.luoyx.hauyne.admin.sys.service.impl;

import com.luoyx.hauyne.admin.api.sys.dto.UserDTO;
import com.luoyx.hauyne.admin.sys.converter.AuthorityConverter;
import com.luoyx.hauyne.admin.sys.entity.Authority;
import com.luoyx.hauyne.admin.sys.mapper.AuthorityMapper;
import com.luoyx.hauyne.admin.sys.query.AuthorityCodeUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.AuthorityNameUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.AuthorityQuery;
import com.luoyx.hauyne.admin.sys.request.AuthorityCreateDTO;
import com.luoyx.hauyne.admin.sys.request.AuthorityUpdateDTO;
import com.luoyx.hauyne.admin.sys.response.AuthorityCheckBoxTreeVO;
import com.luoyx.hauyne.admin.sys.response.AuthorityDetailVO;
import com.luoyx.hauyne.admin.sys.response.AuthorityTreeNodeVO;
import com.luoyx.hauyne.admin.sys.response.AuthorityTreeSelectVO;
import com.luoyx.hauyne.admin.sys.response.AuthorityVO;
import com.luoyx.hauyne.admin.sys.response.DictItemDropdownVO;
import com.luoyx.hauyne.admin.sys.service.AuthorityService;
import com.luoyx.hauyne.admin.sys.service.DictItemService;
import com.luoyx.hauyne.admin.sys.service.RoleAuthorityService;
import com.luoyx.hauyne.admin.util.MenuTreeUtil;
import com.luoyx.hauyne.mybatisplus.service.impl.BaseServiceImpl;
import com.luoyx.hauyne.web.exception.ResourceNotFoundException;
import com.luoyx.hauyne.web.exception.ValidateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author 罗英雄
 * @since 2022-06-18
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl extends BaseServiceImpl<AuthorityMapper, Authority> implements AuthorityService {

    private final RoleAuthorityService roleAuthorityService;
    private final AuthorityConverter authorityConverter;
    private final DictItemService dictItemService;

    @Override
    public Set<String> findAuthoritiesByUserId(Long userId) {
        return baseMapper.findAuthoritiesByUserId(userId);
    }

    @Override
    public List<UserDTO.SideMenuDTO> buildMenuTree(Long sysUserId) {
        List<UserDTO.SideMenuDTO> sideMenuDtoList = new MenuTreeUtil(
                baseMapper.selectSideMenuByUserId(sysUserId)
        ).builTree();

        return sideMenuDtoList;
    }

    @Override
    public List<AuthorityTreeNodeVO> findAuthorities(AuthorityQuery query) {
        List<AuthorityTreeNodeVO> authorityTreeNodeList = new ArrayList<>();
        List<AuthorityVO> authorities = baseMapper.findList(query);

        if (CollectionUtils.isNotEmpty(authorities)) {
            Map<String, String> authorityTypeMap = dictItemService.selectDropdownData("authority_type")
                    .stream()
                    .collect(
                            Collectors.toMap(
                                    DictItemDropdownVO::getValue,
                                    DictItemDropdownVO::getLabel,
                                    (v1, v2) -> v2
                            )
                    );
            authorities.forEach(item -> {
                item.setAuthorityType(authorityTypeMap.get(item.getAuthorityType()));
            });
        }

        // 根节点
        List<AuthorityVO> rootList = authorities.stream()
                .filter(item -> item.getParentId() == 0)
                .collect(Collectors.toList());

        for (AuthorityVO rootNode : rootList) {
            AuthorityTreeNodeVO treeNodeVO = authorityConverter.toAuthorityTreeNode(rootNode);
            authorityTreeNodeList.add(buildChileNode(treeNodeVO, authorities));
        }

        return authorityTreeNodeList;
    }

    private AuthorityTreeNodeVO buildChileNode(AuthorityTreeNodeVO authorityTreeNode,
                                               List<AuthorityVO> authorityList) {
        List<AuthorityTreeNodeVO> children = new ArrayList<>();
        for (AuthorityVO authority : authorityList) {
            if (authority.getParentId().equals(authorityTreeNode.getId())) {
                AuthorityTreeNodeVO treeNode = authorityConverter.toAuthorityTreeNode(authority);
                children.add(buildChileNode(treeNode, authorityList));
            }
        }
        authorityTreeNode.setChildren(children);

        return authorityTreeNode;
    }

    /**
     * 新增权限
     *
     * @param authority
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Authority create(AuthorityCreateDTO authorityCreateDTO) {
        authorityCreateDTO.setAuthorityCode(authorityCreateDTO.getAuthorityCode().trim());
        Authority authority = authorityConverter.toEntity(authorityCreateDTO);

        // 校验表单参数
        checkFormData(authority);

        Long parentId = authority.getParentId();

        // 如果没选择父级菜单，则默认为根菜单
        if (null == parentId) {
            authority.setParentId(0L);
            authority.setLevel(1);
        } else {
            Authority parentAuthority = baseMapper.selectById(parentId);
            if (null == parentAuthority) {
                throw new ResourceNotFoundException("父节点不存在");
            }

            // 如果父节点是叶子节点，则取消其叶子节点
            if (Boolean.TRUE.equals(parentAuthority.getLeaf())) {
                parentAuthority.setLeaf(false);
                baseMapper.updateById(parentAuthority);
            }
            authority.setLevel(parentAuthority.getLevel() + 1);
        }

        // 设置默认图标
        if (StringUtils.isBlank(authority.getIcon())) {
            authority.setIcon("skin");
        }

        // 设置默认序号
        if (null == authority.getSort()) {
            Integer maxSort = baseMapper.selectMaxSortByParentId(authority.getParentId());
            authority.setSort(null == maxSort ? 1 : maxSort + 1);
        }
        baseMapper.insert(authority);

        return authority;
    }

    /**
     * 修改权限
     *
     * @param authorityUpdateDTO 权限表单参数
     */
    @Override
    public void update(AuthorityUpdateDTO authorityUpdateDTO) {
        authorityUpdateDTO.setAuthorityCode(authorityUpdateDTO.getAuthorityCode().trim());
        Authority authority = authorityConverter.toEntity(authorityUpdateDTO);
        if (StringUtils.isBlank(authority.getIcon())) {
            authority.setIcon("skin");
        }
        baseMapper.updateById(authority);
    }

    /**
     * 校验权限编码可用性
     *
     * @param query 权限编码唯一性校验 查询条件
     * @return
     */
    @Override
    public boolean checkAuthorityCodeUnique(AuthorityCodeUniqueCheckQuery query) {
        return baseMapper.countAuthorityCode(query) == 0;
    }

    /**
     * 校验权限名称可用性
     *
     * @param query 权限名称唯一性校验 查询条件
     * @return
     */
    @Override
    public boolean checkAuthorityNameAvailability(AuthorityNameUniqueCheckQuery query) {
        return baseMapper.countAuthorityName(query) == 0;
    }

    /**
     * 校验表单参数
     *
     * @param authority 权限表单参数
     */
    @Override
    public void checkFormData(Authority authority) {
        Long id = authority.getId();
        String authorityName = authority.getAuthorityName();
        if (!checkAuthorityNameAvailability(new AuthorityNameUniqueCheckQuery(id, authorityName))) {
            throw new ValidateException("权限名称" + authorityName + "已存在, 请勿重复添加");
        }
    }

    @Override
    public List<AuthorityTreeSelectVO> selectParentAuthorityFormData() {
        List<AuthorityTreeSelectVO> dataList = baseMapper.selectParentAuthorityFormData();
        List<AuthorityTreeSelectVO> rootNodeList = dataList.stream()
                .filter(item -> item.getParentKey().equals(0L))
                .collect(Collectors.toList());
        for (AuthorityTreeSelectVO rootNode : rootNodeList) {
            buildChilTree(rootNode, dataList);
        }

        return rootNodeList;
    }

    /**
     * 加载权限资源, 以复选框形式的树结构展示
     *
     * @return 权限资源树
     */
    @Override
    public List<AuthorityCheckBoxTreeVO> selectCheckBoxTree() {
        List<AuthorityCheckBoxTreeVO> allNodeList = baseMapper.selectForCheckBoxTree();
        List<AuthorityCheckBoxTreeVO> rootNodeList = allNodeList.stream()
                .filter(item -> "0".equals(item.getParentKey()))
                .collect(Collectors.toList());
        for (AuthorityCheckBoxTreeVO rootNode : rootNodeList) {
            buildChilTree(rootNode, allNodeList);
        }

        return rootNodeList;
    }

    /**
     * 构建父级菜单树【表单】
     *
     * @param parentNode  父节点
     * @param allNodeList 所有节点
     * @return
     */
    private AuthorityTreeSelectVO buildChilTree(AuthorityTreeSelectVO parentNode, List<AuthorityTreeSelectVO> allNodeList) {
        List<AuthorityTreeSelectVO> childrenList = new ArrayList<>();
        for (AuthorityTreeSelectVO node : allNodeList) {
            if (node.getParentKey().equals(parentNode.getKey())) {

                // 非叶子节点执行递归
                childrenList.add(buildChilTree(node, allNodeList));
            }
        }
        parentNode.setChildren(childrenList);

        return parentNode;
    }

    /**
     * 构建父级菜单树【表单】
     *
     * @param parentNode  父节点
     * @param allNodeList 所有节点
     * @return
     */
    private AuthorityCheckBoxTreeVO buildChilTree(AuthorityCheckBoxTreeVO parentNode, List<AuthorityCheckBoxTreeVO> allNodeList) {
        log.info("菜单【{}】是否叶子节点 -> {}", parentNode.getTitle(), parentNode.getLeaf());
        List<AuthorityCheckBoxTreeVO> childrenList = new ArrayList<>();
        for (AuthorityCheckBoxTreeVO node : allNodeList) {
            if (node.getParentKey().equals(parentNode.getKey())) {

                // 非叶子节点执行递归
                childrenList.add(buildChilTree(node, allNodeList));
            }
        }
        parentNode.setChildren(childrenList);

        return parentNode;
    }


    /**
     * 按权限资源id 删除【角色-权限资源】数据以及【权限资源数据】
     * <p>
     * 控制在一个事务下
     *
     * @param id 权限资源id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        Authority existAuthority = baseMapper.selectById(id);
        if (null == existAuthority) {
            throw new ResourceNotFoundException("权限数据id " + id + "不存在");
        }

        // 校验当前权限是否存在子级权限
        final String authorityName = existAuthority.getAuthorityName();
        if (Boolean.FALSE.equals(existAuthority.getLeaf())) {
            throw new ValidateException("无法删除权限【" + authorityName + "】：该权限下仍存在子权限，请先删除所有子权限后重试。");
        }

        // 校验权限资源是否被角色引用
        List<String> roleNames = baseMapper.findRoleNamesByAuthorityId(id);
        if (CollectionUtils.isNotEmpty(roleNames)) {
            int limit = 3;
            List<String> displayNames = roleNames.size() > limit
                    ? roleNames.subList(0, limit)
                    : roleNames;

            String joined = String.join("、", displayNames);
            String suffix = roleNames.size() > limit ? " 等 " + roleNames.size() + " 个角色" : "";

            String message = "无法删除权限「" + authorityName + "」："
                    + "该权限当前被角色「" + joined + suffix + "」引用，"
                    + "请先解除与角色的关联再尝试删除。";

            throw new ValidateException(message);
        }
        roleAuthorityService.deleteByAuthorityId(id);
        baseMapper.deleteById(id);

        long parentId = existAuthority.getParentId();
        long count = baseMapper.selectChildAuthorityByParentId(parentId);

        // 父级权限资源没有子级权限资源时，将父级权限资源设为叶子节点
        if (count == 0) {
            Authority parentAuthority = new Authority();
            parentAuthority.setLeaf(true);
            parentAuthority.setId(parentId);
            baseMapper.updateById(parentAuthority);
        }
    }

    @Override
    public AuthorityDetailVO view(Long id) {
        return baseMapper.view(id);
    }

    /**
     * 查询角色已有的权限名称
     *
     * @param roleId 角色id
     * @return 角色已有的权限名称
     */
    @Override
    public List<String> selectAuthorityNamesByRoleId(Long roleId) {
        return baseMapper.selectAuthorityNamesByRoleId(roleId);
    }

    /**
     * @param authorityIds
     * @return
     */
    @Override
    public List<String> selectAuthorityNamesByIds(List<Long> authorityIds) {
        return baseMapper.selectAuthorityNamesByIds(authorityIds);
    }

    /**
     * 校验权限资源id是否存在
     *
     * @param ids 权限资源id集合
     * @return 权限资源键值对 key=权限资源id, value=权限资源
     */
    @Override
    public Map<Long, Authority> checkAuthorityIds(Set<Long> ids) {
        Map<Long, Authority> authorityMap = baseMapper.selectBatchIds(ids).stream()
                .collect(
                        Collectors.toMap(
                                Authority::getId,
                                item -> item,
                                (oldValue, newValue) -> newValue,
                                LinkedHashMap::new
                        )
                );
        for (Long id : ids) {
            if (!authorityMap.containsKey(id)) {
                throw new ValidateException("不合法的权限资源id【" + id + "】");
            }
        }

        return authorityMap;
    }
}
