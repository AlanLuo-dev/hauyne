//package com.luoyx.hauyne.uaa.authentication.granter;
//
//import com.luoyx.hauyne.uaa.authentication.captcha.CaptchaGrantAuthenticationToken;
//import com.luoyx.hauyne.security.util.OAuth2CookieHelper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.authentication.AccountStatusException;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
//import org.springframework.security.oauth2.provider.*;
//import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
//import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import jakarta.servlet.http.HttpServletResponse;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
///**
// * 自定义Granter: 图形验证码登录(复制密码模式代码修改)
// *
// * @author 罗英雄
// */
//@Slf4j
//public class CaptchaTokenGranter extends AbstractTokenGranter {
//
//    public static final String CAPTCHA_GRANT_TYPE = "captcha";
//
//    private final AuthenticationManager authenticationManager;
//
//    public CaptchaTokenGranter(AuthenticationManager authenticationManager,
//                               AuthorizationServerTokenServices tokenServices,
//                               ClientDetailsService clientDetailsService,
//                               OAuth2RequestFactory requestFactory) {
//        super(tokenServices, clientDetailsService, requestFactory, CAPTCHA_GRANT_TYPE);
//        this.authenticationManager = authenticationManager;
//    }
//
//    @Override
//    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
//
//        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
//        String username = parameters.get("username");
//        String password = parameters.get("password");
//
//        // 防止下游密码泄露
//        parameters.remove("password");
//        String captcha = parameters.get("captcha");
//        String captchaKey = parameters.get("captchaKey");
//
//        Authentication userAuth = new CaptchaGrantAuthenticationToken(username, password, captcha, captchaKey);
//        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
//        try {
//            userAuth = authenticationManager.authenticate(userAuth);
//        } catch (AccountStatusException ase) {
//            //covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
//            throw new InvalidGrantException(ase.getMessage());
//        } catch (BadCredentialsException e) {
//            // If the username/password are wrong the spec says we should send 400/invalid grant
//            throw new InvalidGrantException(e.getMessage());
//        }
//        if (userAuth == null || !userAuth.isAuthenticated()) {
//            throw new InvalidGrantException("Could not authenticate username: " + username);
//        }
//        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
//
//        return new OAuth2Authentication(storedOAuth2Request, userAuth);
//    }
//
//    @Override
//    public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
//        log.info(">>> 正在为客户端【{}】获取AccessToken ", tokenRequest.getClientId());
//        // 调用父类的默认方法
//        OAuth2AccessToken accessToken = super.grant(grantType, tokenRequest);
//
//        log.info("已获得的AccessToken : {} {}", accessToken.getTokenType(), accessToken.getValue());
//
//        // 设置 access_token 和 refresh_token 的 Cookie
//        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
//        OAuth2CookieHelper.storeTokenToCookie(response, accessToken);
//
//        return accessToken;
//    }
//}