package com.luoyx.hauyne.admin.sys.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户 自动完成数据 VO类
 *
 * @Author 罗英雄
 * @Date 2023-04-08 17:45:31
 */
@Getter
@Setter
@ToString
public class UserAutoCompleteVO {

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
     * 用户真实姓名
     */
    @Schema(description = "用户真实姓名")
    private String fullName;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickname;
}
