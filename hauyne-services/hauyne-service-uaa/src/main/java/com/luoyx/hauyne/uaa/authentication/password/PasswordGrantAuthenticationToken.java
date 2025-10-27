package com.luoyx.hauyne.uaa.authentication.password;

import com.luoyx.hauyne.uaa.authentication.OAuth2Constant;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Map;

/**
 * @author luoyingxiong
 */
public class PasswordGrantAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {
    /**
     * Sub-class constructor.
     *
     * @param clientPrincipal      the authorization grant type
     * @param clientPrincipal      the authenticated client principal
     * @param additionalParameters the additional parameters
     */
    protected PasswordGrantAuthenticationToken(Authentication clientPrincipal,
                                               @Nullable Map<String, Object> additionalParameters) {
        super(
                new AuthorizationGrantType(OAuth2Constant.GRANT_TYPE_PASSWORD),
                clientPrincipal,
                additionalParameters
        );
    }
}
