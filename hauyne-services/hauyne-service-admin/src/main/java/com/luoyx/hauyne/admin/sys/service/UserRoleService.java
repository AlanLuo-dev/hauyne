package com.luoyx.hauyne.admin.sys.service;

import com.luoyx.hauyne.admin.sys.entity.UserRole;
import com.luoyx.hauyne.mybatisplus.service.BaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户-角色中间表 服务类
 * </p>
 *
 * @author 罗英雄
 * @since 2022-09-07
 */
public interface UserRoleService extends BaseService<UserRole> {

    /**
     * 统计当前角色的使用人数
     *
     * @param roleIds 角色id集合
     * @return 角色名称
     */
    String countUserRoleByRoleIds(@Param("roleIds") List<Long> roleIds);

    /**
     * 批量删除 用户-角色 关联信息
     *
     * @param userIds 用户id 集合
     */
    void deleteUserRoleByUserIds(List<Long> userIds);

    /**
     * 保存用户角色
     *
     * @param userId  用户id
     * @param roleIds 角色id 集合
     */
    void saveUserRole(Long userId, Set<Long> roleIds);

    /**
     * 更新用户角色（先删除后新增）
     *
     * @param userId  用户id
     * @param roleIds 角色id 集合
     */
    void updateUserRoleByUserId(Long userId, Set<Long> roleIds);
}
