package com.luoyx.hauyne.idempotent.keygen;

import com.luoyx.hauyne.idempotent.annotation.Idempotent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;


/**
 * 生成lock key
 *
 * @author 汪小哥
 * @date 10-04-2021
 */
public interface LockKeyGenerator {
    /**
     * 没有找到值
     */
    String NOT_FOUND = "NOT_FOUND_VALUE";

    /**
     * 获取处理缓存key
     *
     * @param idempotent    注解
     * @param request       请求
     * @param response      响应
     * @param handlerMethod 方法
     * @return
     */
    String resolverLockKey(Idempotent idempotent, HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod);
}
