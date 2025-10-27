//package com.luoyx.hauyne.uaa.aspect;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
////import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
///**
// * 认证AOP切面
// *
// * @author 罗英雄
// * @date 2021/4/22 16:34
// */
//@Slf4j
//@Component
//@Aspect
//public class AuthTokenAspect {
//
//    /**
//     * 刷新令牌模式
//     */
//    private static final String REFRESH_TOKEN_GRANT_TYPE =  "refresh_token";
//
//    /**
//     * 切点表达式
//     * 切入 /oauth/token 请求
//     */
//    @Pointcut("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
//    public void postAccessTokenPointCut() {
//    }
//
//    /**
//     * @param point
//     * @return
//     * @throws Throwable
//     */
//    @Around("postAccessTokenPointCut()")
//    public Object handleControllerMethod(ProceedingJoinPoint point) throws Throwable {
//        log.info(">> 开始执行/oauth/token接口的切面");
//        // 获取方法参数（Authentication auth, Map<String, String> parameters）
//        Object[] args = point.getArgs();
//        Authentication authentication = (Authentication) args[0];
//        @SuppressWarnings("unchecked")
//        Map<String, String> parameters = (Map<String, String>) args[1];
//
//        // 打印 grant_type 及 client_id
//        String grantType = parameters.get("grant_type");
//        String clientId = authentication.getName();  // 客户端ID
//
//        log.info(">> grant_type: {}", grantType);
//        log.info(">> client_id: {}", clientId);
//        log.info(">> 请求参数: {}", parameters);
//        Object proceedResult = point.proceed();
//        log.info(">> 结束执行/oauth/token接口的切面");
//        if (proceedResult != null) {
//            ResponseEntity<OAuth2AccessToken> responseEntity = (ResponseEntity<OAuth2AccessToken>) proceedResult;
//
//            if (responseEntity.getStatusCode().is2xxSuccessful()) {
//                OAuth2AccessToken oauth2AccessToken = responseEntity.getBody();
//                log.info("令牌结果\n: {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(oauth2AccessToken));
//
////                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
////                HttpServletRequest request = attributes.getRequest();
////                HttpServletResponse response = attributes.getResponse();
//
////                String grantType = request.getParameter("grant_type");
//
//                /**
//                 * 【登录场景】针对自定义的captcha授权模式，将token信息写入到Cookie
//                 * 【刷新令牌场景】 针对refresh_token模式，将token信息写入到Cookie
//                 */
////                if (CaptchaTokenGranter.CAPTCHA_GRANT_TYPE.equals(grantType) || REFRESH_TOKEN_GRANT_TYPE.equals(grantType)) {
////
////                    // 访问令牌
////                    Cookie accessTokenCookie = new Cookie(OAuth2AccessToken.ACCESS_TOKEN, oauth2AccessToken.getValue());
////                    accessTokenCookie.setPath("/");
////                    accessTokenCookie.setMaxAge(oauth2AccessToken.getExpiresIn());
////                    accessTokenCookie.setHttpOnly(true);
////                    response.addCookie(accessTokenCookie);
////
////
////                    /**
////                     * 刷新令牌设置到Cookie中
////                     */
////                    DefaultExpiringOAuth2RefreshToken defaultExpiringOAuth2RefreshToken = (DefaultExpiringOAuth2RefreshToken)oauth2AccessToken.getRefreshToken();
////                    Cookie refreshTokenCookie = new Cookie(OAuth2AccessToken.REFRESH_TOKEN, defaultExpiringOAuth2RefreshToken.getValue());
////
////                    //设置刷新令牌的Path, 仅限 /oauth/token请求路径发送refresh_token。
////                    refreshTokenCookie.setPath("/api/uaa/oauth/token");
////                    refreshTokenCookie.setDomain("localhost");
////
////                    // refresh_token的Cookie值得过期时间设置为 refresh_token本身的过期时间
////                    refreshTokenCookie.setMaxAge(Long.valueOf((defaultExpiringOAuth2RefreshToken.getExpiration().getTime() - System.currentTimeMillis()) / 1000L).intValue());
////                    refreshTokenCookie.setHttpOnly(true);
////                    response.addCookie(refreshTokenCookie);
////
////                    return null;
////                }
//            } else {
//                log.error("error:{}", responseEntity.getStatusCode().toString());
//            }
//        }
//
//        return proceedResult;
//    }
//}
