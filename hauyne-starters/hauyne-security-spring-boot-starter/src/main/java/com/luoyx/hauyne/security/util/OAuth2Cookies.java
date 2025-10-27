package com.luoyx.hauyne.security.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;


/**
 * 存储访问令牌和刷新令牌的 Cookie。
 */
@Getter
class OAuth2Cookies {
    /**
     * 访问令牌 Cookie
     */
    private Cookie accessTokenCookie;

    /**
     * 刷新令牌 Cookie
     */
    private Cookie refreshTokenCookie;

    public void setCookies(Cookie accessTokenCookie, Cookie refreshTokenCookie) {
        this.accessTokenCookie = accessTokenCookie;
        this.refreshTokenCookie = refreshTokenCookie;
    }

    /**
     * 在成功认证后将访问令牌和刷新令牌作为 Cookie 添加到响应中。
     *
     * @param response 要添加到的响应对象。
     */
    void addCookiesTo(HttpServletResponse response) {
        response.addCookie(getAccessTokenCookie());
        response.addCookie(getRefreshTokenCookie());
    }
}
