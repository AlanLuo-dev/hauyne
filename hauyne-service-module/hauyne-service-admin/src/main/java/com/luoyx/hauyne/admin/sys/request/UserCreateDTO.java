package com.luoyx.hauyne.admin.sys.request;

import com.luoyx.hauyne.admin.sys.enums.EnabledEnum;
import com.luoyx.hauyne.admin.sys.enums.GenderEnum;
import com.luoyx.hauyne.validation.constraint.EnumCheck;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * 新增用户入参 DTO
 *
 * @author Alan.Luo
 */
@Data
public class UserCreateDTO {

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    @NotBlank(message = "用户名必填")
    @Length(max = 50, message = "用户名长度不能超过50")
    private String username;

    /**
     * 登录密码
     */
    @Schema(description = "登录密码")
    @NotBlank(message = "登录密码必填")
    @Length(max = 50, message = "登录密码长度不能超过50")
    private String password;

    /**
     * 是否启用，true=是，false=否
     */
    @Schema(description = "是否启用")
    private EnabledEnum enabled;

    /**
     * 用户资料
     */
    @Schema(description = "用户资料")
    @NotNull(message = "用户资料必填")
    @Valid
    private UserProfileDTO profile;

    @Data
    public static class UserProfileDTO {
        /**
         * 用户真实姓名
         */
        @Schema(description = "用户真实姓名")
        @Length(max = 50, message = "用户真实姓名长度不能超过50")
        private String realName;

        /**
         * 昵称
         */
        @Schema(description = "昵称")
        @Length(max = 50, message = "昵称长度不能超过50")
        private String nickname;

        /**
         * 头像
         */
        @Schema(description = "头像")
        @Length(max = 255, message = "头像长度不能超过255")
        private String avatar;

        /**
         * 性别
         */
        @Schema(description = "性别")
//        @EnumCheck(enumClazz = GenderEnum.class, getterMethod = "getValue")
        private GenderEnum gender;

        /**
         * 出生日期
         */
        @Schema(description = "出生日期")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @PastOrPresent(message = "出生日期不能是未来时间")
        private LocalDate birthday;

        /**
         * 手机号
         */
        @Schema(description = "手机号")
        @Length(max = 50, message = "手机号长度不能超过50")
        private String phone;

        /**
         * 电子邮箱
         */
        @Schema(description = "电子邮箱")
        @Length(max = 50, message = "电子邮箱长度不能超过50")
        private String email;


        /**
         * 职位
         */
        @Schema(description = "职位")
        @Length(max = 50, message = "职位长度不能超过50")
        private String position;

        /**
         * 备注
         */
        @Schema(description = "备注")
        @Length(max = 255, message = "备注长度不能超过255")
        private String remark;
    }

    /**
     * 角色Id列表
     */
    @Schema(description = "角色Id列表")
    @NotNull(message = "角色必填")
    @NotEmpty(message = "角色不能为空")
    private List<@NotNull(message = "角色Id不能为空") Long> roleIds;
}
