//package com.luoyx.hauyne.uaa.config;
//
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
///**
// * 为UAA授权服务器配置单独的OpenFeign令牌中继拦截器
// * UAA服务不使用org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor，
// * 因为UAA只做授权服务器 & 资源服务器，不配置为OAuth2客户端
// *
// * @author Alan.Luo
// */
//@Component
//public class TokenRelayRequestInterceptor implements RequestInterceptor {
//
//    @Override
//    public void apply(RequestTemplate template) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth instanceof OAuth2Authentication) {
//            OAuth2Authentication oauth = (OAuth2Authentication) auth;
//            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) oauth.getDetails();
//            String tokenValue = details.getTokenValue();
//            if (StringUtils.hasText(tokenValue)) {
//                template.header("Authorization", "Bearer " + tokenValue);
//            }
//        }
//    }
//}
//
