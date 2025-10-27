package com.luoyx.hauyne.uaa.controller;

import com.luoyx.hauyne.uaa.response.AuthenticationStatusVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author ex-luoyingxiong
 * @date 2020/7/29 10:38
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class MeController {

    /**
     * 获取当前的用户
     *
     * @return 当前用户信息
     */
//    @GetMapping("/me")
//    public AuthenticationStatusVO me() {
//        AuthenticationStatusVO authenticationStatus = new AuthenticationStatusVO();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            boolean authenticated = authentication.isAuthenticated()
//                    && authentication instanceof OAuth2Authentication
//                    && authentication.getPrincipal() instanceof CurrentSysUser;
//            authenticationStatus.setAuthenticated(authenticated);
//            if (authentication.getPrincipal() instanceof CurrentSysUser) {
//                authenticationStatus.setPrincipal((CurrentSysUser) authentication.getPrincipal());
//            }
//        }
//
//        return authenticationStatus;
//    }
    @GetMapping("/me")
    public AuthenticationStatusVO me() {
        AuthenticationStatusVO authenticationStatus = new AuthenticationStatusVO();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {

            authenticationStatus.setAuthenticated(true);

            if (authentication instanceof BearerTokenAuthentication bearerTokenAuthentication) {
                Map<String, Object> tokenAttributes = bearerTokenAuthentication.getTokenAttributes();
                authenticationStatus.setPrincipal(tokenAttributes.get("principal"));
            }
        } else {
            authenticationStatus.setAuthenticated(false);
        }

        return authenticationStatus;
    }


    /**
     * 获取当前的用户（For oauth2-resource-dev.yml配置的 security.oauth2.resource.user-info-uri ）
     *
     * @return
     */
//    @GetMapping(value = "/currentLoginedUser")
//    public Object currentLoginedUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication instanceof OAuth2Authentication) {
//            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
//            Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
//            if (null == userAuthentication) {
//
//                return oAuth2Authentication;
//            }
//
//            // 对应 授权服务器的Authentication(在这里是Map结构)
//            Object details = userAuthentication.getPrincipal();
//
//            return details;
//        }
//
//        return authentication;
//    }


//    @GetMapping("/foo")
//    public String foo(BearerTokenAuthentication authentication) {
//        return authentication.getTokenAttributes().get("sub") + " is the subject";
//    }

    @GetMapping("/foo")
    public String foo(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        return principal.getAttribute("sub") + " is the subject";
    }
}
