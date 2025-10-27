package com.luoyx.hauyne.admin.sys.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.luoyx.hauyne.admin.sys.entity.Role;
import com.luoyx.hauyne.admin.sys.query.RoleCodeUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.RoleNameUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.response.RoleDropdownVO;
import com.luoyx.hauyne.mybatisplus.mapper.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author 罗英雄
 * @since 2022-07-30
 */
public interface RoleMapper extends GenericMapper<Role> {

    /**
     * 角色编码唯一性校验
     *
     * @param query 角色编码唯一性校验查询参数
     * @return 返回大于0 则表示角色编码已存在，返回0 则表示角色编码可用
     */
    default Long countByRoleCode(RoleCodeUniqueCheckQuery query) {
        return selectCount(
                Wrappers.<Role>lambdaQuery()
                        .eq(Role::getRoleCode, query.getRoleCode())
                        .ne(query.getExcludeRoleId() != null, Role::getId, query.getExcludeRoleId())
        );
    }

    /**
     * 角色名称唯一性校验
     *
     * @param query 角色名称唯一性校验查询参数
     * @return 返回大于0 则表示角色名称已存在，返回0 则表示角色名称可用
     */
    default Long countByRoleName(RoleNameUniqueCheckQuery query) {
        return selectCount(
                Wrappers.<Role>lambdaQuery()
                        .eq(Role::getRoleName, query.getRoleName())
                        .ne(query.getExcludeRoleId() != null, Role::getId, query.getExcludeRoleId())
        );
    }

    /**
     * 查询角色已有的权限（只查询叶子节点，前端Ng-Zorro的复选框树会根据子节点的选中情况决定的父节点是否半选中）
     *
     * @param roleId 角色id
     * @return 权限资源id集合(叶子节点)
     */
    List<String> selectLeafNodeAuthorityIdsByRoleId(@Param("roleId") Long roleId);

    List<Role> selectAll();

    /**
     * 查询角色下拉框数据列表
     *
     * @return 角色下拉框数据列表
     */
    List<RoleDropdownVO> selectDropdown();
}
