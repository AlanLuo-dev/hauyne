package com.luoyx.hauyne.admin.sys.service;

import com.luoyx.hauyne.admin.api.sys.dto.UserDTO;
import com.luoyx.hauyne.admin.sys.entity.Authority;
import com.luoyx.hauyne.admin.sys.query.AuthorityCodeUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.AuthorityNameUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.AuthorityQuery;
import com.luoyx.hauyne.admin.sys.request.AuthorityCreateDTO;
import com.luoyx.hauyne.admin.sys.request.AuthorityUpdateDTO;
import com.luoyx.hauyne.admin.sys.response.AuthorityCheckBoxTreeVO;
import com.luoyx.hauyne.admin.sys.response.AuthorityDetailVO;
import com.luoyx.hauyne.admin.sys.response.AuthorityTreeNodeVO;
import com.luoyx.hauyne.admin.sys.response.AuthorityTreeSelectVO;
import com.luoyx.hauyne.mybatisplus.service.BaseService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author 罗英雄
 * @since 2022-06-18
 */
public interface AuthorityService extends BaseService<Authority> {

    /**
     * 按用户id查询权限
     *
     * @param userId
     * @return
     */
    Set<String> findAuthoritiesByUserId(Long userId);

    List<UserDTO.SideMenuDTO> buildMenuTree(Long sysUserId);


    /**
     * 查询权限数据（树形结构展示）
     *
     * @param query 过滤条件
     * @return
     */
    List<AuthorityTreeNodeVO> findAuthorities(AuthorityQuery query);

    /**
     * 新增权限
     *
     * @param authorityCreateDTO 权限表单参数
     * @return 权限数据
     */
    Authority create(AuthorityCreateDTO authorityCreateDTO);

    /**
     * 修改权限
     *
     * @param authorityUpdateDTO 权限表单参数
     */
    void update(AuthorityUpdateDTO authorityUpdateDTO);

    /**
     * 校验权限编码可用性
     *
     * @param query 权限编码唯一性校验 查询条件
     * @return
     */
    boolean checkAuthorityCodeUnique(AuthorityCodeUniqueCheckQuery query);

    /**
     * 校验权限名称可用性【修改场景】
     *
     * @param query 权限名称唯一性校验 查询条件
     * @return
     */
    boolean checkAuthorityNameAvailability(AuthorityNameUniqueCheckQuery query);

    /**
     * 校验表单参数
     *
     * @param authority 权限表单参数
     */
    void checkFormData(Authority authority);

    List<AuthorityTreeSelectVO> selectParentAuthorityFormData();

    /**
     * 加载权限资源, 以复选框形式的树结构展示
     *
     * @return 权限资源树
     */
    List<AuthorityCheckBoxTreeVO> selectCheckBoxTree();

    /**
     * 按权限资源id 删除【角色-权限资源】数据以及【权限资源数据】
     *
     * @param id
     */
    void deleteById(Long id);

    AuthorityDetailVO view(Long id);

    /**
     * 查询角色已有的权限名称
     *
     * @param roleId 角色id
     * @return 角色已有的权限名称
     */
    List<String> selectAuthorityNamesByRoleId(Long roleId);

    List<String> selectAuthorityNamesByIds(List<Long> authorityIds);

    /**
     * 校验权限资源id是否存在
     *
     * @param ids 权限资源id集合
     * @return 权限资源键值对 key=权限资源id, value=权限资源
     */
    Map<Long, Authority> checkAuthorityIds(Set<Long> ids);
}
