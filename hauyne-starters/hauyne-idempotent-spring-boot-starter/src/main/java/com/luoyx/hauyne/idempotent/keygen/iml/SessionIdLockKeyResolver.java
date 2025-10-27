package com.luoyx.hauyne.idempotent.keygen.iml;

import com.luoyx.hauyne.idempotent.annotation.Idempotent;
import com.luoyx.hauyne.idempotent.keygen.LockKeyGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;


/**
 * sessionId
 *
 * @author 汪小哥
 * @date 14-04-2021
 */
@Component
public class SessionIdLockKeyResolver implements LockKeyGenerator {

    @Override
    public String resolverLockKey(Idempotent idempotent, HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) {
        String sessionId = request.getSession().getId();
        if (!StringUtils.hasText(sessionId)) {
            sessionId = NOT_FOUND;
        }
        return String.format("%s_%s", sessionId, request.getRequestURI());
    }
}
