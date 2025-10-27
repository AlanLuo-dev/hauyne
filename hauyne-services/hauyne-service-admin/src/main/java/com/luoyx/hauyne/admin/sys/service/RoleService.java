package com.luoyx.hauyne.admin.sys.service;

import com.luoyx.hauyne.admin.sys.entity.Role;
import com.luoyx.hauyne.admin.sys.query.RoleCodeUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.RoleNameUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.request.RoleCreateDTO;
import com.luoyx.hauyne.admin.sys.request.RoleUpdateDTO;
import com.luoyx.hauyne.admin.sys.response.RoleDropdownVO;
import com.luoyx.hauyne.mybatisplus.service.BaseService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author 罗英雄
 * @since 2022-07-30
 */
public interface RoleService extends BaseService<Role> {

    /**
     * 校验角色编码可用性
     *
     * @param query
     * @return
     */
    boolean checkRoleCodeUnique(RoleCodeUniqueCheckQuery query);

    /**
     * 校验角色名称可用性
     *
     * @param query
     * @return
     */
    boolean checkRoleNameUnique(RoleNameUniqueCheckQuery query);

    /**
     * 表单参数校验
     *
     * @param role
     */
    void checkRoleFormData(Role role);

    /**
     * 新增角色
     *
     * @param role
     * @return
     */
    Role create(RoleCreateDTO role);

    /**
     * 修改角色
     *
     * @param role
     */
    void update(RoleUpdateDTO role);

    /**
     * 删除角色
     *
     * @param ids 角色id集合
     */
    void deleteByIds(List<Long> ids);

    /**
     * 更新角色的权限资源（先删后增）
     *
     * @param roleId       角色id
     * @param authorityIds 权限资源id数组
     */
    void updateRoleAuthorities(Long roleId, List<Long> authorityIds);

    /**
     * 查询角色已有的权限（只查询叶子节点，前端Ng-Zorro的复选框树会根据子节点的选中情况决定的父节点是否半选中）
     *
     * @param roleId 角色id
     * @return 权限资源id集合(叶子节点)
     */
    List<String> selectLeafNodeAuthorityIdsByRoleId(Long roleId);

    List<Role> selectAll();

    /**
     * 查询角色下拉框数据列表
     *
     * @return 角色下拉框数据列表
     */
    List<RoleDropdownVO> selectDropdown();

    /**
     * 校验角色是否存在
     *
     * @param roleId 角色id
     * @return 角色信息
     */
    Role checkRoleExists(Long roleId);

    /**
     * 校验角色是否存在
     *
     * @param roleIds 角色id集合
     * @return 角色信息列表
     */
    List<Role> checkRolesExist(Set<Long> roleIds);
}
