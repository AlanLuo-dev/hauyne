package com.luoyx.hauyne.uaa.config;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@Component
public class CookieTokenResponseHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        if (!(authentication instanceof OAuth2AccessTokenAuthenticationToken tokenAuthentication)) {
            return;
        }

        OAuth2AccessToken accessToken = tokenAuthentication.getAccessToken();
        OAuth2RefreshToken refreshToken = tokenAuthentication.getRefreshToken();

        // 将 Access Token 设置为 HttpOnly Cookie
        ResponseCookie accessTokenCookie = ResponseCookie.from("access_token", accessToken.getTokenValue())
                .httpOnly(true)
                .path("/")
                .maxAge(accessToken.getExpiresAt().getEpochSecond() - Instant.now().getEpochSecond())
                .build();

        // 可选：将 Refresh Token 也设置为 Cookie
        ResponseCookie refreshTokenCookie = null;
        if (refreshToken != null) {
            refreshTokenCookie = ResponseCookie.from("refresh_token", refreshToken.getTokenValue())
                    .httpOnly(true)
                    .path("/api/uaa/oauth2/token")
                    .maxAge(Duration.ofDays(30))
                    .build();
        }

        response.setHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        if (refreshTokenCookie != null) {
            response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
        }

        // 清空响应体
        response.setContentLength(0);
    }
}

