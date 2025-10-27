package com.luoyx.hauyne.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.luoyx.hauyne.mybatisplus.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author 罗英雄
 * @since 2022-06-22
 */
@Getter
@Setter
@ToString
@TableName("hyn_sys_user")
@SuppressWarnings("serial")
public class User extends BaseEntity<User> {

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
    @TableField(value = "is_account_non_expired")
    private Boolean accountNonExpired;

    /**
     * 帐户是否未锁定（1=是 0=否）
     */
    @TableField(value = "is_account_non_locked")
    private Boolean accountNonLocked;

    /**
     * 凭据（即密码）是否未过期（1=是 0=否）
     */
    @TableField(value = "is_credentials_non_expired")
    private Boolean credentialsNonExpired;

    /**
     * 是否可用（1=是 0=否）
     */
    @TableField(value = "is_enabled")
    private Boolean enabled;

    /**
     * 修改密码的时间
     */
    private LocalDateTime passwordChangeTime;

    /**
     * 最近登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 最近登录IP
     */
    private String lastLoginIp;

    /**
     * 累计登录次数
     */
    private Long loginCount;
}
