package com.luoyx.hauyne.idempotent.keygen.iml;

import com.luoyx.hauyne.idempotent.annotation.Idempotent;
import com.luoyx.hauyne.idempotent.keygen.LockKeyGenerator;
import com.luoyx.hauyne.idempotent.utils.IpUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ip+getRequestURI  锁定
 *
 * @author 汪小哥
 * @date 10-04-2021
 */
@Component(value = "idempotentIpLockKeyResolver")
public class IpLockKeyResolver implements LockKeyGenerator {

    @Override
    public String resolverLockKey(Idempotent idempotent, HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) {
        String ip = IpUtils.getIpAddress(request);
        return String.format("%s_%s", ip, request.getRequestURI());
    }
}
