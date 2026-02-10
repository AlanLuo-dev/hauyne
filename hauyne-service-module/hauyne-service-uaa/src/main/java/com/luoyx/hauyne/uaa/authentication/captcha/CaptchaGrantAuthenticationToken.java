package com.luoyx.hauyne.uaa.authentication.captcha;

import com.luoyx.hauyne.uaa.authentication.OAuth2Constant;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Collection;
import java.util.Map;

/**
 * 自定义 Token类
 */
public class CaptchaGrantAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {
    /**
     * Sub-class constructor.
     *
     * @param clientPrincipal      the authorization grant type
     * @param clientPrincipal      the authenticated client principal
     * @param additionalParameters the additional parameters
     */
    protected CaptchaGrantAuthenticationToken(Authentication clientPrincipal,
                                              @Nullable Map<String, Object> additionalParameters) {
        super(
                new AuthorizationGrantType(OAuth2Constant.GRANT_TYPE_CAPTCHA),
                clientPrincipal,
                additionalParameters
        );
    }
}

//public class CaptchaGrantAuthenticationToken extends AbstractAuthenticationToken {
//    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
//
//    private final Object principal;
//    private Object credentials;
//    private String captcha;
//    private String captchaKey;
//
//
//    public CaptchaGrantAuthenticationToken(String username,
//                                           String password,
//                                           String captcha,
//                                           String captchaKey) {
//        super(null);
//        this.principal = username;
//        this.credentials = password;
//        this.captcha = captcha;
//        this.captchaKey = captchaKey;
//        setAuthenticated(false);
//    }
//
//    public CaptchaGrantAuthenticationToken(Object principal, Object credentials,
//                                           Collection<? extends GrantedAuthority> authorities) {
//        super(authorities);
//        this.principal = principal;
//        this.credentials = credentials;
//        super.setAuthenticated(true);
//    }
//
//    @Override
//    public Object getCredentials() {
//        return this.credentials;
//    }
//
//    @Override
//    public Object getPrincipal() {
//        return this.principal;
//    }
//
//    @Override
//    public void setAuthenticated(boolean isAuthenticated) {
//        if (isAuthenticated) {
//            throw new IllegalArgumentException(
//                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
//        }
//        super.setAuthenticated(false);
//    }
//
//    @Override
//    public void eraseCredentials() {
//        super.eraseCredentials();
//        credentials = null;
//    }
//
//
//    public void setCredentials(Object credentials) {
//        this.credentials = credentials;
//    }
//
//    public String getCaptcha() {
//        return captcha;
//    }
//
//    public void setCaptcha(String captcha) {
//        this.captcha = captcha;
//    }
//
//    public String getCaptchaKey() {
//        return captchaKey;
//    }
//
//    public void setCaptchaKey(String captchaKey) {
//        this.captchaKey = captchaKey;
//    }
//}
