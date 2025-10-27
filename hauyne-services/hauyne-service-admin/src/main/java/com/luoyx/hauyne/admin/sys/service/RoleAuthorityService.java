package com.luoyx.hauyne.admin.sys.service;

import com.luoyx.hauyne.admin.sys.entity.RoleAuthority;
import com.luoyx.hauyne.mybatisplus.service.BaseService;

import java.util.List;

/**
 * <p>
 * 角色-权限中间表 服务类
 * </p>
 *
 * @author 罗英雄
 * @since 2022-07-30
 */
public interface RoleAuthorityService extends BaseService<RoleAuthority> {

    /**
     * 按权限资源id删除 角色-权限资源关联记录
     *
     * @param authorityId 权限资源id
     */
    void deleteByAuthorityId(Long authorityId);

    /**
     * 按角色id删除 角色-权限资源关联记录
     *
     * @param roleIds 角色id集合
     */
    void deleteByRoleIds(List<Long> roleIds);
}
