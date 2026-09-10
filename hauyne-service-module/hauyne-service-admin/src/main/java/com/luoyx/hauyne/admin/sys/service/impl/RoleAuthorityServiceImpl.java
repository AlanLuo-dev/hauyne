package com.luoyx.hauyne.admin.sys.service.impl;

import com.luoyx.hauyne.admin.sys.entity.Role;
import com.luoyx.hauyne.admin.sys.entity.RoleAuthority;
import com.luoyx.hauyne.admin.sys.mapper.RoleAuthorityMapper;
import com.luoyx.hauyne.admin.sys.mapper.RoleMapper;
import com.luoyx.hauyne.admin.sys.service.RoleAuthorityService;
import com.luoyx.hauyne.mybatisplus.service.impl.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 角色-权限中间表 服务实现类
 * </p>
 *
 * @author 罗英雄
 * @since 2022-07-30
 */
@Service
@RequiredArgsConstructor
public class RoleAuthorityServiceImpl extends BaseServiceImpl<RoleAuthorityMapper, RoleAuthority> implements RoleAuthorityService {

    private final RoleMapper roleMapper;

    /**
     * 按权限资源id删除 角色-权限资源关联记录
     *
     * @param authorityId 权限资源id
     */
    @Override
    public void deleteByAuthorityId(Long authorityId) {
        baseMapper.deleteByAuthorityId(authorityId);
    }

    @Override
    public void deleteByRoleIds(Collection<Long> roleIds) {
        baseMapper.deleteByRoleIds(roleIds);
    }

    @Override
    public void grantNewAuthorityToSuperAdminRole(Long authorityId) {
        Role superAdminRole = roleMapper.selectSuperAdminRole();
        if (superAdminRole == null) {
            throw new IllegalStateException(
                    "系统超级管理员角色 SUPER_ADMIN 不存在"
            );
        }

        RoleAuthority roleAuthority = new RoleAuthority();
        roleAuthority.setAuthorityId(authorityId);
        roleAuthority.setRoleId(superAdminRole.getId());

        baseMapper.insert(roleAuthority);
    }
}
