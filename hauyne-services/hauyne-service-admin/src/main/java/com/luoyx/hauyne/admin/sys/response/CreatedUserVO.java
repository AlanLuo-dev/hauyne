package com.luoyx.hauyne.admin.sys.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;




/**
 * 已新增的用户信息
 *
 * @author Alan.Luo
 */
@Data
public class CreatedUserVO {

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
     * 是否已启用（true= 已启用，false= 未启用）
     */
    @Schema(description = "是否已启用（true= 已启用，false= 未启用）")
    private Boolean enabled;

    /**
     * 用户资料
     */
    @Schema(description = "用户资料")
    private UserProfileVO profile;

    @Data
    public static class UserProfileVO {

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
         * 性别（1= 男，0= 女）
         */
        @Schema(description = "性别（1= 男，0= 女）")
        private Integer gender;

        /**
         * 头像
         */
        @Schema(description = "头像")
        private String avatar;

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

    /**
     * 角色列表
     */
    @Schema(description = "角色列表")
    private List<String> roleNames;
}

