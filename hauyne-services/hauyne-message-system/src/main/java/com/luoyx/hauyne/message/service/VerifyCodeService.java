package com.luoyx.hauyne.message.service;

import com.luoyx.hauyne.message.support.VerifyCode;

/**
 * @author 罗英雄
 * @date 2021/9/12 14:48
 */
public interface VerifyCodeService {

    VerifyCode getCode();

    boolean check(String token, String code);
}
