package com.luoyx.hauyne.admin.sys.response;

import com.luoyx.hauyne.admin.api.sys.enums.EnabledEnum;
import com.luoyx.hauyne.admin.api.sys.enums.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 用户 表单回显VO类
 * </p>
 *
 * @author Alan.Luo
 */
@Getter
@Setter
@ToString
public class UserEditFormVO {

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private Long id;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 是否启用，true=是，false=否
     */
    @Schema(description = "是否启用")
    private EnabledEnum enabled;

    /**
     * 角色Id列表
     */
    @Schema(description = "角色Id列表")
    private List<Long> roleIds;

    /**
     * 用户资料
     */
    @Schema(description = "用户资料")
    private UserProfileDTO profile;

    @Data
    public static class UserProfileDTO {
        /**
         * 用户真实姓名
         */
        @Schema(description = "用户真实姓名")
        private String realName;

        /**
         * 昵称
         */
        @Schema(description = "昵称")
        private String nickname;

        /**
         * 头像
         */
        @Schema(description = "头像")
        private String avatar;

        /**
         * 性别
         */
        @Schema(description = "性别")
        private GenderEnum gender;

        /**
         * 出生日期
         */
        @Schema(description = "出生日期")
        private LocalDate birthday;

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
         * 职位
         */
        @Schema(description = "职位")
        private String position;

        /**
         * 备注
         */
        @Schema(description = "备注")
        private String remark;
    }
}
