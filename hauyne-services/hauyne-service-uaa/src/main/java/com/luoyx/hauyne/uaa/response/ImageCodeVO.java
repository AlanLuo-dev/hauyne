package com.luoyx.hauyne.uaa.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 返回给前端的 验证码图片和RSA公钥
 *
 * @author 罗英雄
 */
@Getter
@Setter
public class ImageCodeVO {

    @Schema(description = "验证码图片base64字符串")
    private String base64ImageCode;

    @Schema(description = "RSA公钥")
    private String rsaPublicKey;

    @Schema(description = "captchaKey")
    private String captchaKey;

    @Override
    public String toString() {
        return "ImageCodeVO{" +
                "base64ImageCode='" + base64ImageCode + '\'' +
                ", rsaPublicKey='" + rsaPublicKey + '\'' +
                ", captchaKey='" + captchaKey + '\'' +
                '}';
    }
}
