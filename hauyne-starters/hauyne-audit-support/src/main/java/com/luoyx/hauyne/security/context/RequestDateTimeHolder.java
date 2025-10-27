package com.luoyx.hauyne.security.context;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * 用于保存本次请求的当前时间
 * <p>
 * 1. 在当前线程下，多个写入动作的操作时间（最常见的如创建时间、修改时间），都使用同一个时间
 *
 * @author xxx
 */
@Slf4j
public class RequestDateTimeHolder {

    private static final ThreadLocal<LocalDateTime> CONTEXT = new ThreadLocal<>();

    public static void set(LocalDateTime nowTime) {
        CONTEXT.set(nowTime);
        if (log.isTraceEnabled()) {
            log.trace("setNowTime -> {}", nowTime);
        }
    }

    public static LocalDateTime get() {
        LocalDateTime nowTime = CONTEXT.get();
        if (log.isTraceEnabled()) {
            log.trace("getNowTime -> {}", nowTime);
        }

        return nowTime;
    }

    public static void clear() {
        LocalDateTime nowTime = CONTEXT.get();
        CONTEXT.remove();
        if (log.isTraceEnabled()) {
            log.trace("clear NowTime -> {}", nowTime);
        }
    }
}


