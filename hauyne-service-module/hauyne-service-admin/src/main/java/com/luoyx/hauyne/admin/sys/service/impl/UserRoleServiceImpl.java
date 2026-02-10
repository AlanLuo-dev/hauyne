package com.luoyx.hauyne.admin.sys.service.impl;

import com.luoyx.hauyne.admin.sys.entity.UserRole;
import com.luoyx.hauyne.admin.sys.mapper.UserRoleMapper;
import com.luoyx.hauyne.admin.sys.service.UserRoleService;
import com.luoyx.hauyne.mybatisplus.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户-角色中间表 服务实现类
 * </p>
 *
 * @author 罗英雄
 * @since 2022-09-07
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
    /**
     * 统计当前角色的使用人数
     *
     * @param roleIds 角色id集合
     * @return 角色名称
     */
    @Override
    public String countUserRoleByRoleIds(List<Long> roleIds) {
        return baseMapper.countUserRoleByRoleIds(roleIds);
    }

    /**
     * 批量删除 用户-角色 关联信息
     *
     * @param userIds 用户id 集合
     */
    @Override
    public void deleteUserRoleByUserIds(List<Long> userIds) {
        baseMapper.deleteUserRoleByUserIds(userIds);
    }

    /**
     * 保存用户角色
     *
     * @param userId  用户id
     * @param roleIds 角色id 集合
     */
    @Override
    public void saveUserRole(Long userId, Set<Long> roleIds) {
        List<UserRole> userRoleList = roleIds.stream()
                .map(roleId -> UserRole.builder().userId(userId).roleId(roleId).build())
                .toList();
        baseMapper.insert(userRoleList);
    }

    /**
     * 更新用户角色（先删除后新增）
     *
     * @param userId  用户id
     * @param roleIds 角色id 集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserRoleByUserId(Long userId, Set<Long> roleIds) {
        baseMapper.deleteUserRoleByUserIds(Collections.singletonList(userId));
        saveUserRole(userId, roleIds);
    }
}
