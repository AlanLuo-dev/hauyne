package com.luoyx.hauyne.message.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

/**
 * @author 罗英雄
 * @date 2021/9/12 14:48
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerifyCode {

    private String token;
    private String code;
    private String image;
}
