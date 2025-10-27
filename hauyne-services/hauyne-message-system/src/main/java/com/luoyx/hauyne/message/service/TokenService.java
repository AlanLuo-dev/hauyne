package com.luoyx.hauyne.message.service;

import com.luoyx.hauyne.message.support.CheckResult;

import java.util.concurrent.TimeUnit;

/**
 * @author 罗英雄
 * @date 2021/9/12 14:45
 */
public interface TokenService {

    String getToken(final long timeout, final TimeUnit unit, String data);

    CheckResult check(String token);
}
