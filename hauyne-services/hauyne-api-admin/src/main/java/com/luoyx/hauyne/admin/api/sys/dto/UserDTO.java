package com.luoyx.hauyne.admin.api.sys.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 用户DTO类
 *
 * @author 1564469545@qq.com
 */
@NoArgsConstructor
@Data
public class UserDTO {

    /**
     * 用户基本信息（一对一）
     */
    public UserBaseInfo userBaseInfo;

    /**
     * 用户档案信息（一对一）
     */
    private UserProfileDTO userProfile;

    /**
     * 拥有的权限
     */
    private Set<String> authorities = Collections.emptySet();

    /**
     * 侧边树形菜单
     */
    private List<SideMenuDTO> sideMenuList = Collections.emptyList();

    @NoArgsConstructor
    @Data
    public static class UserBaseInfo {
        /**
         * 用户Id(主键)
         */
        private Long id;

        /**
         * 用户名
         */
        private String username;

        /**
         * 密码
         */
        private String password;

        /**
         * 帐户是否未过期（1=是 0=否）
         */
        private boolean accountNonExpired;

        /**
         * 帐户是否未锁定（1=是 0=否）
         */
        private boolean accountNonLocked;

        /**
         * 凭据（即密码）是否未过期（1=是 0=否）
         */
        private boolean credentialsNonExpired;

        /**
         * 是否可用（1=是 0=否）
         */
        private boolean enabled;
    }

    @NoArgsConstructor
    @Data
    public static class UserProfileDTO {

        /**
         * 昵称
         */
        private String nickname;

        /**
         * 真实姓名
         */
        private String realName;

        /**
         * 性别(1=男 0=女)
         */
        private Integer gender;

        /**
         * 头像地址
         */
        private String avatar;

        /**
         * 手机号
         */
        private String phone;

        /**
         * 电子邮箱
         */
        private String email;

        /**
         * 职位
         */
        private String position;
    }

    /**
     * 侧边树形菜单DTO类
     *
     * @author 罗英雄 luo_yingxiong@163.com
     */
    @NoArgsConstructor
    @Data
    public static class SideMenuDTO {

        /**
         * 菜单id
         */
        private Long id;

        /**
         * 父菜单id
         */
        private Long parentId;

        /**
         * 权限树层级（1=第1层，2=第2层，以此类推）
         */
        private Integer level;

        /**
         * 菜单名
         */
        private String label;

        /**
         * 图标
         */
        private String icon;

        /**
         * 链接
         */
        private String routerLink;

        /**
         * 子菜单列表
         */
        private List<SideMenuDTO> items;
    }
}
