//package com.luoyx.hauyne.uaa.authentication.password_backup;
//
//import com.luoyx.hauyne.uaa.authentication.OAuth2Constant;
//import org.springframework.lang.Nullable;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.core.AuthorizationGrantType;
//import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;
//import org.springframework.util.Assert;
//
//import java.util.Map;
//
///**
// * @author Rommel
// * @version 1.0
// * @date 2023/7/20-14:31
// * @description TODO
// */
//public class PasswordGrantAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {
//
//    public PasswordGrantAuthenticationToken(Authentication clientPrincipal,
//                                            @Nullable Map<String, Object> additionalParameters) {
//        super(
//                new AuthorizationGrantType(OAuth2Constant.GRANT_TYPE_PASSWORD),
//                clientPrincipal,
//                additionalParameters
//        );
//    }
//}
