package com.luoyx.hauyne.admin.sys.mapper;

import com.luoyx.hauyne.admin.sys.entity.RoleAuthority;
import com.luoyx.hauyne.mybatisplus.mapper.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色-权限资源中间表 Mapper 接口
 * </p>
 *
 * @author 罗英雄
 * @since 2022-07-30
 */
public interface RoleAuthorityMapper extends GenericMapper<RoleAuthority> {
    /**
     * 按权限资源id删除 角色-权限资源关联记录
     *
     * @param authorityId 权限资源id
     */
    void deleteByAuthorityId(@Param("authorityId") Long authorityId);

    /**
     * 按角色id删除 角色-权限资源关联记录
     *
     * @param roleIds 角色id
     */
    void deleteByRoleIds(@Param("roleIds") List<Long> roleIds);
}
