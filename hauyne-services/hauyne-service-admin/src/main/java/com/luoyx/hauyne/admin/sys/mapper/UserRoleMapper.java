package com.luoyx.hauyne.admin.sys.mapper;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.luoyx.hauyne.admin.sys.entity.UserRole;
import com.luoyx.hauyne.mybatisplus.mapper.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户-角色中间表 Mapper 接口
 * </p>
 *
 * @author 罗英雄
 * @since 2022-09-07
 */
public interface UserRoleMapper extends GenericMapper<UserRole> {

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
    default void deleteUserRoleByUserIds(List<Long> userIds) {
        LambdaUpdateWrapper<UserRole> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(UserRole::getUserId, userIds);
        delete(updateWrapper);
    }

    /**
     * 查询用户的角色id集合
     *
     * @param userId 用户id
     * @return 角色id集合
     */
    List<Long> selectRoleIdsByUserId(Long userId);
}
