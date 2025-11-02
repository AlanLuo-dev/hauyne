package com.luoyx.hauyne.uaa.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class ClearCookieOnRevokeSuccessHandler implements AuthenticationSuccessHandler {

    private final String[] cookieNames;

    public ClearCookieOnRevokeSuccessHandler(String[] cookieNames) {
        this.cookieNames = cookieNames;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {

        Cookie accessTokenCookie = new Cookie(cookieNames[0], "");
        accessTokenCookie.setPath("/");
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setMaxAge(0);    // 删除 Cookie
        response.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie(cookieNames[1], "");
        refreshTokenCookie.setPath("/api/uaa/oauth2/token");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge(0);    // 删除 Cookie
        response.addCookie(refreshTokenCookie);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}

