package com.luoyx.hauyne.admin.sys.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 重置密码 DTO
 *
 * @author Alan.Luo
 */
@Data
public class ResetPasswordDTO {

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    /**
     * key
     */
    @Schema(description = "redis中私钥的key")
    @NotBlank(message = "key不能为空")
    private String key;

    /**
     * 密码
     */
    @Schema(description = "密码(RSA密文)")
    @NotBlank(message = "密码不能为空")
    private String encryptPassword;
}
