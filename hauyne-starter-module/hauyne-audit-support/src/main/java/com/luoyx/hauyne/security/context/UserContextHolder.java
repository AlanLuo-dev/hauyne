package com.luoyx.hauyne.security.context;

import lombok.extern.slf4j.Slf4j;

/**
 * UserContextHolder用于保存当前线程的用户ID，以便在审计中进行使用。它是一个ThreadLocal实例，
 * 可以在整个应用程序中提供唯一的用户ID，以便在请求链中共享该信息。在设置和获取用户ID时，都会记录到日志中。
 *
 * @author 1564469545@qq.com
 * @since 0.1.0
 */
@Slf4j
public class UserContextHolder {
    private static final ThreadLocal<Long> contextHolder = new ThreadLocal<>();

    public static void setUserId(Long userId) {
        contextHolder.set(userId);
        if (log.isTraceEnabled()) {
            log.trace("setUserId -> {}", userId);
        }
    }

    public static Long getUserId() {
        Long userId = contextHolder.get();
        if (log.isTraceEnabled()) {
            log.trace("getUserId -> {}", userId);
        }

        return userId;
    }

    public static void clear() {
        Long userId = contextHolder.get();
        contextHolder.remove();
        if (log.isTraceEnabled()) {
            log.trace("clear -> {}", userId);
        }

    }
}
