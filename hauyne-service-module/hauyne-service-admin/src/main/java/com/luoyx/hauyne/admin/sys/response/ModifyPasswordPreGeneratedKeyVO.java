package com.luoyx.hauyne.admin.sys.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 修改密码预生成的密钥（RSA）
 *
 * @Author 罗英雄
 * @Date 2023-01-15 09:05:46
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ModifyPasswordPreGeneratedKeyVO {

    /**
     * RSA公钥
     */
    @Schema(description = "RSA公钥")
    private String rsaPublicKey;

    /**
     * key
     */
    @Schema(description = "redis中私钥的key")
    private String key;
}
