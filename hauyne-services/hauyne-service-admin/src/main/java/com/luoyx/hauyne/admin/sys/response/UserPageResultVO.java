package com.luoyx.hauyne.admin.sys.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luoyx.hauyne.admin.sys.enums.AccountNonExpiredEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统用户 VO类
 * </p>
 *
 * @author LuoYingxiong
 * @since 2022-06-11
 */
@Getter
@Setter
@ToString
public class UserPageResultVO {

    /**
     * 主键id
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 角色名
     */
    @Schema(description = "角色名")
    private String roleName;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 电子邮箱
     */
    @Schema(description = "电子邮箱")
    private String email;

    /**
     * 帐户过期状态
     */
    @Schema(description = "帐户过期状态")
    private AccountNonExpiredEnum accountNonExpired;

    /**
     * 帐户是否未锁定
     */
    @JsonIgnore
    private Boolean accountNonLockedValue;

    /**
     * 帐户锁定状态
     */
    @Schema(description = "帐户锁定状态")
    private String accountNonLocked;

    /**
     * 密码是否未过期
     */
    @JsonIgnore
    private Boolean credentialsNonExpiredValue;

    /**
     * 密码过期状态
     */
    @Schema(description = "密码过期状态")
    private String credentialsNonExpired;

    /**
     * 是否已启用 原始值
     */
    @JsonIgnore
    private Boolean enabledValue;

    /**
     * 是否已启用 文本值
     */
    @Schema(description = "是否已启用")
    private String enabled;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickname;

    /**
     * 用户真实姓名
     */
    @Schema(description = "用户真实姓名")
    private String realName;

    /**
     * 性别
     */
    @JsonIgnore
    private Integer genderValue;

    /**
     * 性别
     */
    @Schema(description = "性别")
    private String gender;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 职位
     */
    @Schema(description = "职位")
    private String position;


    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;


    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    /**
     * 最后修改时间
     */
    @Schema(description = "最后修改时间")
    private LocalDateTime lastUpdatedTime;

    /**
     * 是否是自己
     */
    @Schema(description = "是否是自己（true=是，false=否）")
    private Boolean self;
}
