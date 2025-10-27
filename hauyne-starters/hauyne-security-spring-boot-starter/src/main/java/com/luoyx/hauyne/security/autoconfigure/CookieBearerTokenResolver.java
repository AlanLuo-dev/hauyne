package com.luoyx.hauyne.security.autoconfigure;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;

public class CookieBearerTokenResolver implements BearerTokenResolver {

    private static final String ACCESS_TOKEN_COOKIE_NAME = "access_token";

    @Override
    public String resolve(HttpServletRequest request) {

        // 再从 Cookie 中读取
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (ACCESS_TOKEN_COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}

