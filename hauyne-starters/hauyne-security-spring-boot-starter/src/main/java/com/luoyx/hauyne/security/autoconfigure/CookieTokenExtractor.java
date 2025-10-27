//package com.luoyx.hauyne.security.autoconfigure;
//
//import com.luoyx.hauyne.security.util.OAuth2CookieHelper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
//
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//
///**
// * 令牌提取器【优先从Cookie中提取】
// *
// * @author luoyingxiong
// */
//@Slf4j
//public class CookieTokenExtractor extends BearerTokenExtractor {
//
//    /**
//     * 重写 BearerTokenExtractor类的 extractToken方法
//     * 优先从请求的Cookie中提取访问令牌。如果存在，则将令牌返回
//     * <p>
//     *
//     * @param request
//     * @return 返回 AccessToken 或 NULL
//     */
//    @Override
//    protected String extractToken(HttpServletRequest request) {
//        String result;
//        Cookie accessTokenCookie = OAuth2CookieHelper.getAccessTokenCookie(request);
//        if (accessTokenCookie != null) {
//            result = accessTokenCookie.getValue();
//            log.warn("从Cookie中取 AccessToken -> {}", result);
//        } else {
//
//            // 如果Cookie中不存在，则继续执行 BearerTokenExtractor类的 extractToken方法【即尝试从请求头的Authorization参数获取，如果不存在，再尝试从请求体的参数中获取】
//            result = super.extractToken(request);
//            log.warn("从Authorization 中取 AccessToken -> {}", result);
//        }
//
//        return result;
//    }
//}
