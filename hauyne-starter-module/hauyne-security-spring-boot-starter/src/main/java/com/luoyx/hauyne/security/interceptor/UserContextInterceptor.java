package com.luoyx.hauyne.security.interceptor;

import com.luoyx.hauyne.security.context.UserContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class UserContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return true;
        }

        if (authentication instanceof BearerTokenAuthentication bearer) {
            Map<String, Object> attributes = bearer.getTokenAttributes();
            Object principal = attributes.get("principal");
            if (principal instanceof Map<?, ?> principalMap) {
                Object userId = principalMap.get("id");
                if (userId != null) {
                    UserContextHolder.setUserId(Long.valueOf(userId.toString()));
                    log.trace("已设置 UserContextHolder，userId={}", userId);
                }
            }
        }

        return true;
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler,
                                Exception ex) {

        try {
            UserContextHolder.clear();
        } finally {
            log.trace("已清除 UserContextHolder");
        }
    }
}
