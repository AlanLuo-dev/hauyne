package com.luoyx.hauyne.security.filter;

import com.luoyx.hauyne.security.context.UserContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author luoyingxiong
 */
@Slf4j
public class UserContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof BearerTokenAuthentication bearerTokenAuthentication) {
            Map<String, Object> tokenAttributes = bearerTokenAuthentication.getTokenAttributes();
            Object principal = tokenAttributes.get("principal");
            LinkedHashMap<String, Object> principalMap = (LinkedHashMap<String, Object>) principal;
            Object userId = principalMap.get("id");
            UserContextHolder.setUserId(Long.valueOf(userId.toString()));
            if (log.isTraceEnabled()) {
                log.trace("已设置 userId");
            }
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            UserContextHolder.clear();
            if (log.isTraceEnabled()) {
                log.trace("已清除 userId");
            }
        }
    }


}
