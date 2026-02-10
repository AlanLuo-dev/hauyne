package com.luoyx.hauyne.uaa.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Component
public class CookieRefreshTokenFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;

        // 只处理 token endpoint 的 POST（按需调整）
        if ("/oauth2/token".equals(request.getRequestURI())
                && "POST".equalsIgnoreCase(request.getMethod())) {

            // 如果请求里已经有 refresh_token，就不做任何事
            if (request.getParameter("refresh_token") == null) {
                // 从 Cookie 取 refresh_token
                String refreshToken = Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                        .filter(c -> "refresh_token".equals(c.getName()))
                        .map(Cookie::getValue)
                        .findFirst()
                        .orElse(null);

                if (refreshToken != null) {
                    // 包装请求，注入 refresh_token 参数
                    HttpServletRequest wrapped = new ParameterOverrideRequestWrapper(request,
                            Collections.singletonMap("refresh_token", new String[]{refreshToken}));
                    chain.doFilter(wrapped, res);
                    return;
                }
            }
        }

        chain.doFilter(req, res);
    }
}
