package com.luoyx.hauyne.uaa.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.core.Authentication;

public class ClearCookieOnRevokeSuccessHandler implements AuthenticationSuccessHandler {

    private final String[] cookieNames;

    public ClearCookieOnRevokeSuccessHandler(String[] cookieNames) {
        this.cookieNames = cookieNames;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) {

        for (String cookieName : cookieNames) {
            Cookie cookie = new Cookie(cookieName, "");
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setSecure(true); // 如果应用跑在 https 下，建议启用
            cookie.setMaxAge(0);    // 删除 Cookie
            response.addCookie(cookie);
        }

        response.setStatus(HttpServletResponse.SC_OK);
    }
}

