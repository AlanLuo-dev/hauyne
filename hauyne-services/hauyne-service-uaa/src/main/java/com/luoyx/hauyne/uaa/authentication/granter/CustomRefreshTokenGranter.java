//package com.luoyx.hauyne.uaa.authentication.granter;
//
//import com.luoyx.hauyne.security.util.OAuth2CookieHelper;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.provider.ClientDetails;
//import org.springframework.security.oauth2.provider.ClientDetailsService;
//import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
//import org.springframework.security.oauth2.provider.TokenRequest;
//import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
//import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import jakarta.servlet.http.HttpServletResponse;
//
///**
// * @author 1564469545@qq.com
// * @date 2023/3/6 21:48
// */
//public class CustomRefreshTokenGranter extends RefreshTokenGranter {
//
//    private static final String REFRESH_TOKEN_GRANT_TYPE = "refresh_token";
//
//    public CustomRefreshTokenGranter(AuthorizationServerTokenServices tokenServices,
//                                     ClientDetailsService clientDetailsService,
//                                     OAuth2RequestFactory requestFactory) {
//        super(tokenServices, clientDetailsService, requestFactory, REFRESH_TOKEN_GRANT_TYPE);
//    }
//
//    @Override
//    protected OAuth2AccessToken getAccessToken(ClientDetails client, TokenRequest tokenRequest) {
//        String refreshToken = tokenRequest.getRequestParameters().get("refresh_token");
//        OAuth2AccessToken accessToken = getTokenServices().refreshAccessToken(refreshToken, tokenRequest);
//
//        // 设置 access_token 和 refresh_token 的 Cookie
//        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
//        if (null != response && "service-admin".equals(tokenRequest.getClientId())) {
//            OAuth2CookieHelper.storeTokenToCookie(response, accessToken);
//        }
//
//        return accessToken;
//    }
//}
