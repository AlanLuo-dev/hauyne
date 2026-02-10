package com.luoyx.hauyne.admin.sys.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 修改密码 表单参数VO
 *
 * @Author 罗英雄
 * @Date 2023-01-08 16:48:41
 */
@Getter
@Setter
@ToString
public class ModifyPasswordDTO {

    /**
     * key
     */
    @Schema(description = "redis中私钥的key")
    @NotBlank(message = "key不能为空")
    private String key;

    /**
     * 旧密码
     */
    @Schema(description = "旧密码(Base64(RSA公钥加密))")
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

    /**
     * 新密码
     */
    @Schema(description = "新密码(Base64(RSA公钥加密))")
    @NotBlank(message = "新密码不能为空")
    private String newPassword;

    /**
     * 确认密码
     */
    @Schema(description = "确认密码(Base64(RSA公钥加密))")
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;
}
