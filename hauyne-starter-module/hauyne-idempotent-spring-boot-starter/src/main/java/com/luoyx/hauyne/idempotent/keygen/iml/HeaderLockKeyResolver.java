package com.luoyx.hauyne.idempotent.keygen.iml;

import com.luoyx.hauyne.idempotent.autoconfigure.IdempotentProperties;
import com.luoyx.hauyne.idempotent.annotation.Idempotent;
import com.luoyx.hauyne.idempotent.keygen.LockKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Header 作为锁key
 *
 * @author 汪小哥
 * @date 14-04-2021
 */
@Component
public class HeaderLockKeyResolver implements LockKeyGenerator {

    @Autowired
    private IdempotentProperties idempotentProperties;

    @Override
    public String resolverLockKey(Idempotent idempotent, HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) {
        String lockKey = NOT_FOUND;
        if (StringUtils.hasText(idempotentProperties.getDefaultLockKeyHttpHeaderName())) {
            lockKey = request.getHeader(idempotentProperties.getDefaultLockKeyHttpHeaderName());
        }
        if (!StringUtils.hasText(lockKey)) {
            lockKey = NOT_FOUND;
        }
        return String.format("%s_%s", lockKey, request.getRequestURI());
    }
}
