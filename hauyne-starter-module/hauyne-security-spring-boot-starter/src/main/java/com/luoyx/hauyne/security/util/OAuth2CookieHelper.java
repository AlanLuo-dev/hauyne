//package com.luoyx.hauyne.security.util;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.conn.util.InetAddressUtils;
//import org.apache.http.conn.util.PublicSuffixMatcher;
//import org.springframework.boot.json.JsonParser;
//import org.springframework.boot.json.JsonParserFactory;
//import org.springframework.security.jwt.Jwt;
//import org.springframework.security.jwt.JwtHelper;
//import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.common.OAuth2RefreshToken;
//import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
//import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
///**
// * Helps with OAuth2 cookie handling.
// *
// * @author copy
// */
//@Slf4j
//@Component
//public class OAuth2CookieHelper {
//
//    /**
//     * 访问令牌在Cookie中的名称
//     */
//    public static final String ACCESS_TOKEN_COOKIE = OAuth2AccessToken.ACCESS_TOKEN;
//
//    /**
//     * 记住我的情况下，刷新令牌的cookie名称。
//     */
//    public static final String REFRESH_TOKEN_COOKIE = OAuth2AccessToken.REFRESH_TOKEN;
//
//    /**
//     * Name of the session-only refresh token in case the user did not check remember me.
//     * 仅会话刷新令牌的名称，以防用户没有勾选“记得我”。
//     */
//    public static final String SESSION_TOKEN_COOKIE = "session_token";
//
//    /**
//     * 我们设置的Cookie的名称列表。
//     */
//    private static final List<String> COOKIE_NAMES = Arrays.asList(ACCESS_TOKEN_COOKIE, REFRESH_TOKEN_COOKIE,
//            SESSION_TOKEN_COOKIE);
//    /**
//     * Number of seconds to expire refresh token cookies before the enclosed token expires.
//     * This makes sure we don't run into race conditions where the cookie is still there but
//     * expires while we process it.
//     * <p>
//     * 在所包含的令牌过期之前，刷新令牌 Cookie 过期的秒数。
//     * 这样可以确保我们不会在处理时遇到仍然存在但已过期的Cookie的竞争条件。
//     * 或者说
//     * 这样可以确保我们不会遇到竞态条件，即 Cookie 仍然存在但在我们处理时已过期。
//     */
//    private static final long REFRESH_TOKEN_EXPIRATION_WINDOW_SECS = 3L;
//
//    /**
//     * 公共后缀匹配器（用于去除Cookie作用域中的私有子域）。
//     */
//    PublicSuffixMatcher suffixMatcher;
//
//    private OAuth2Properties oAuth2Properties;
//
//    /**
//     * Used to parse JWT claims.
//     */
//    private JsonParser jsonParser = JsonParserFactory.getJsonParser();
//
////    public OAuth2CookieHelper(OAuth2Properties oAuth2Properties) {
////        this.oAuth2Properties = oAuth2Properties;
//
//    // Alternatively, always get an up-to-date list by passing an URL
////        this.suffixMatcher = PublicSuffixMatcherLoader.getDefault();
////    }
//
//    public static Cookie getAccessTokenCookie(HttpServletRequest request) {
//        return getCookie(request, ACCESS_TOKEN_COOKIE);
//    }
//
//    public static Cookie getRefreshTokenCookie(HttpServletRequest request) {
//        Cookie cookie = getCookie(request, REFRESH_TOKEN_COOKIE);
//        if (cookie == null) {
//            cookie = getCookie(request, SESSION_TOKEN_COOKIE);
//        }
//        return cookie;
//    }
//
//
//    /**
//     * 从给定的servlet请求中按名称获取Cookie
//     *
//     * @param request    包含cookie的请求
//     * @param cookieName 要获取的cookie的区分大小写的名称
//     * @return 返回生成的Cookie;如果没有找到，则为null
//     */
//    private static Cookie getCookie(HttpServletRequest request, String cookieName) {
//        if (request.getCookies() != null) {
//            for (Cookie cookie : request.getCookies()) {
//                if (cookie.getName().equals(cookieName)) {
//                    String value = cookie.getValue();
//                    if (StringUtils.hasText(value)) {
//                        return cookie;
//                    }
//                }
//            }
//        }
//
//        return null;
//    }
//
//    /**
//     * 使用提供的值创建cookie。
//     *
//     * @param request     当前请求
//     * @param accessToken 要保存到Cookie的 access_token 和 refresh_token
//     * @param rememberMe  用户是否选中了“记住我”。
//     * @param result      will get the resulting cookies set.
//     */
//    public void createCookies(HttpServletRequest request, OAuth2AccessToken accessToken, boolean rememberMe,
//                              OAuth2Cookies result) {
//        String domain = getCookieDomain(request);
//        log.debug("creating cookies for domain {}", domain);
//        Cookie accessTokenCookie = new Cookie(ACCESS_TOKEN_COOKIE, accessToken.getValue());
//        setCookieProperties(accessTokenCookie, request.isSecure(), domain);
//        log.debug("created access token cookie '{}'", accessTokenCookie.getName());
//
//        OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
//        Cookie refreshTokenCookie = createRefreshTokenCookie(refreshToken, rememberMe);
//        setCookieProperties(refreshTokenCookie, request.isSecure(), domain);
//        log.debug("created refresh token cookie '{}', age: {}", refreshTokenCookie.getName(), refreshTokenCookie
//                .getMaxAge());
//
//        result.setCookies(accessTokenCookie, refreshTokenCookie);
//    }
//
//    /**
//     * Create a cookie out of the given refresh token.
//     * Refresh token cookies contain the base64 encoded refresh token (a JWT token).
//     * They also contain a hint whether the refresh token was for remember me or not.
//     * If not, then the cookie will be prefixed by the timestamp it was created at followed by a pipe '|'.
//     * This gives us the chance to expire session cookies regardless of the token duration.
//     */
//    private Cookie createRefreshTokenCookie(OAuth2RefreshToken refreshToken, boolean rememberMe) {
//        int maxAge = -1;
//        String name = SESSION_TOKEN_COOKIE;
//        String value = refreshToken.getValue();
//        if (rememberMe) {
//            name = REFRESH_TOKEN_COOKIE;
//            //get expiration in seconds from the token's "exp" claim
//            Integer exp = getClaim(refreshToken.getValue(), AccessTokenConverter.EXP, Integer.class);
//            if (exp != null) {
//                int now = (int) (System.currentTimeMillis() / 1000L);
//                maxAge = exp - now;
//                log.debug("refresh token valid for another {} secs", maxAge);
//                //let cookie expire a bit earlier than the token to avoid race conditions
//                maxAge -= REFRESH_TOKEN_EXPIRATION_WINDOW_SECS;
//            }
//        }
//        Cookie refreshTokenCookie = new Cookie(name, value);
//        refreshTokenCookie.setMaxAge(maxAge);
//
//        return refreshTokenCookie;
//    }
//
//    /**
//     * Returns true if the refresh token cookie was set with remember me checked.
//     * We can recognize this by the name of the cookie.
//     *
//     * @param refreshTokenCookie the cookie holding the refresh token.
//     * @return true, if it was set persistently (i.e. for "remember me").
//     */
//    public static boolean isRememberMe(Cookie refreshTokenCookie) {
//        return refreshTokenCookie.getName().equals(REFRESH_TOKEN_COOKIE);
//    }
//
//    /**
//     * Extracts the refresh token from the refresh token cookie.
//     * Since we encode additional information into the cookie, this needs to be called to get
//     * hold of the enclosed JWT.
//     *
//     * @param refreshCookie the cookie we store the value in.
//     * @return the refresh JWT from the cookie.
//     */
//    public static String getRefreshTokenValue(Cookie refreshCookie) {
//        String value = refreshCookie.getValue();
//        int i = value.indexOf('|');
//        if (i > 0) {
//            return value.substring(i + 1);
//        }
//        return value;
//    }
//
//    /**
//     * Checks if the refresh token session has expired.
//     * Only makes sense for non-persistent cookies, i.e. when remember me was not checked.
//     * The motivation for this is that we want to throw out a user after a while if he's inactive.
//     * We cannot do this via refresh token validity because that one is also used for remember me.
//     *
//     * @param refreshCookie the refresh token cookie to check.
//     * @return true, if the session is expired.
//     */
////    public boolean isSessionExpired(Cookie refreshCookie) {
////        if (isRememberMe(refreshCookie)) {       //no session expiration for "remember me"
////            return false;
////        }
////        //read non-remember-me session length in secs
////        int validity = oAuth2Properties.getWebClientConfiguration().getSessionTimeoutInSeconds();
////        if (validity < 0) {           //no session expiration configured
////            return false;
////        }
////        Integer iat = getClaim(refreshCookie.getValue(), "iat", Integer.class);
////        if (iat == null) {           //token creating timestamp in secs is missing, session does not expire
////            return false;
////        }
////        int now = (int) (System.currentTimeMillis() / 1000L);
////        int sessionDuration = now - iat;
////        log.debug("session duration {} secs, will timeout at {}", sessionDuration, validity);
////        return sessionDuration > validity;            //session has expired
////    }
//
//    /**
//     * Retrieve the given claim from the given token.
//     *
//     * @param refreshToken the JWT token to examine.
//     * @param claimName    name of the claim to get.
//     * @param clazz        the Class we expect to find there.
//     * @return the desired claim.
//     * @throws InvalidTokenException if we cannot find the claim in the token or it is of wrong type.
//     */
//    @SuppressWarnings("unchecked")
//    private <T> T getClaim(String refreshToken, String claimName, Class<T> clazz) {
//        Jwt jwt = JwtHelper.decode(refreshToken);
//        String claims = jwt.getClaims();
//        Map<String, Object> claimsMap = jsonParser.parseMap(claims);
//        Object claimValue = claimsMap.get(claimName);
//        if (claimValue == null) {
//            return null;
//        }
//        if (!clazz.isAssignableFrom(claimValue.getClass())) {
//            throw new InvalidTokenException("claim is not of expected type: " + claimName);
//        }
//        return (T) claimValue;
//    }
//
//    /**
//     * Set cookie properties of access and refresh tokens.
//     *
//     * @param cookie   the cookie to modify.
//     * @param isSecure whether it is coming from a secure request.
//     * @param domain   the domain for which the cookie is valid. If null, then will fall back to default.
//     */
//    private void setCookieProperties(Cookie cookie, boolean isSecure, String domain) {
//
//        // 拒绝JavaScript访问该Cookie值
//        cookie.setHttpOnly(true);
//        cookie.setPath("/");
//        cookie.setSecure(isSecure);       //if the request comes per HTTPS set the secure option on the cookie
//        if (domain != null) {
//            cookie.setDomain(domain);
//        }
//    }
//
//    /**
//     * Logs the user out by clearing all cookies.
//     *
//     * @param request  the request containing the Cookies.
//     * @param response the response used to clear them.
//     */
//    public void clearCookies(HttpServletRequest request, HttpServletResponse response) {
//        String domain = getCookieDomain(request);
//        for (String cookieName : COOKIE_NAMES) {
//            clearCookie(request, response, domain, cookieName);
//        }
//    }
//
//    private void clearCookie(HttpServletRequest request, HttpServletResponse response,
//                             String domain, String cookieName) {
//        Cookie cookie = new Cookie(cookieName, "");
//        setCookieProperties(cookie, request.isSecure(), domain);
//        cookie.setMaxAge(0);
//        response.addCookie(cookie);
//        log.debug("clearing cookie {}", cookie.getName());
//    }
//
//    /**
//     * Returns the top level domain of the server from the request. This is used to limit the Cookie
//     * to the top domain instead of the full domain name.
//     * <p>
//     * A lot of times, individual gateways of the same domain get their own subdomain but authentication
//     * shall work across all subdomains of the top level domain.
//     * <p>
//     * For example, when sending a request to <code>app1.domain.com</code>,
//     * this returns <code>.domain.com</code>.
//     *
//     * @param request the HTTP request we received from the client.
//     * @return the top level domain to set the cookies for.
//     * Returns null if the domain is not under a public suffix (.com, .co.uk), e.g. for localhost.
//     */
//    private String getCookieDomain(HttpServletRequest request) {
//        String domain = oAuth2Properties.getWebClientConfiguration().getCookieDomain();
//        if (domain != null) {
//            return domain;
//        }
//        // if not explicitly defined, use top-level domain
//        domain = request.getServerName().toLowerCase();
//        // strip off leading www.
//        if (domain.startsWith("www.")) {
//            domain = domain.substring(4);
//        }
//        // if it isn't an IP address
//        if (!InetAddressUtils.isIPv4Address(domain) && !InetAddressUtils.isIPv6Address(domain)) {
//            // strip off private subdomains, leaving public TLD only
//            String suffix = suffixMatcher.getDomainRoot(domain);
//            if (suffix != null && !suffix.equals(domain)) {
//                // preserve leading dot
//                return "." + suffix;
//            }
//        }
//        // no top-level domain, stick with default domain
//        return null;
//    }
//
//    /**
//     * Strip our token cookies from the array.
//     *
//     * @param cookies the cookies we receive as input.
//     * @return the new cookie array without our tokens.
//     */
////    Cookie[] stripCookies(Cookie[] cookies) {
////        CookieCollection cc = new CookieCollection(cookies);
////        if (cc.removeAll(COOKIE_NAMES)) {
////            return cc.toArray();
////        }
////        return cookies;
////    }
//    public static void storeTokenToCookie(HttpServletResponse response, OAuth2AccessToken accessToken) {
//
//        // 访问令牌
//        Cookie accessTokenCookie = new Cookie(OAuth2AccessToken.ACCESS_TOKEN, accessToken.getValue());
//        accessTokenCookie.setPath("/");
//        accessTokenCookie.setMaxAge(accessToken.getExpiresIn());
//        accessTokenCookie.setHttpOnly(true);
//        response.addCookie(accessTokenCookie);
//
//
//        /**
//         * 刷新令牌设置到Cookie中
//         */
//        DefaultExpiringOAuth2RefreshToken defaultExpiringOAuth2RefreshToken = (DefaultExpiringOAuth2RefreshToken) accessToken.getRefreshToken();
//        Cookie refreshTokenCookie = new Cookie(OAuth2AccessToken.REFRESH_TOKEN, defaultExpiringOAuth2RefreshToken.getValue());
//
//        // 设置刷新令牌的Path, 仅限 /oauth/token请求路径发送refresh_token。
//        refreshTokenCookie.setPath("/api/uaa/oauth/token");
//        refreshTokenCookie.setDomain("localhost");
//
//        // refresh_token的Cookie值得过期时间设置为 refresh_token本身的过期时间
//        refreshTokenCookie.setMaxAge(Long.valueOf((defaultExpiringOAuth2RefreshToken.getExpiration().getTime() - System.currentTimeMillis()) / 1000L).intValue());
//        refreshTokenCookie.setHttpOnly(true);
//        response.addCookie(refreshTokenCookie);
//    }
//}
