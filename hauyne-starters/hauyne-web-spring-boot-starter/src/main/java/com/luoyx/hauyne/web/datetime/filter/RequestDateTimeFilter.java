package com.luoyx.hauyne.web.datetime.filter;

import com.luoyx.hauyne.security.context.RequestDateTimeHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 请求时间上下文过滤器：在每个 HTTP 请求进入时设置当前时间，
 * 用于后续请求链中统一使用（如日志记录、审计字段填充等）。
 * <p>
 * 使用 ThreadLocal 方式避免多线程数据污染，
 * 并在请求结束后清理上下文，防止内存泄漏。
 */
@Slf4j
public class RequestDateTimeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            LocalDateTime now = LocalDateTime.now();
            RequestDateTimeHolder.set(now);

            if (log.isTraceEnabled()) {
                log.trace("Set request datetime: {}", now);
            }

            filterChain.doFilter(request, response);
        } finally {
            RequestDateTimeHolder.clear();
            if (log.isTraceEnabled()) {
                log.trace("Cleared request datetime context");
            }
        }
    }
}
