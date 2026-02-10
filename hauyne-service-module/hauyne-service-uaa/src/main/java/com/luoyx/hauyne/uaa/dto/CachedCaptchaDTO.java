package com.luoyx.hauyne.uaa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 生成的图形验证码实际值和rsa私钥缓存到Redis
 *
 * @author 罗英雄
 * @date 2019/5/2320:26
 */
@Getter
@Setter
public class CachedCaptchaDTO implements Serializable {

    @Schema(description = "验证码真实值")
    private String actualImgCode;

    @Schema(description = "RSA私钥，用于解密前端发送的密码密文")
    private String rsaPrivateKey;

    @Override
    public String toString() {
        return "CachedCaptchaDTO{" +
                "actualImgCode='" + actualImgCode + '\'' +
                ", rsaPrivateKey='" + rsaPrivateKey + '\'' +
                '}';
    }
}