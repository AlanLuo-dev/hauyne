package com.luoyx.hauyne.uaa.authentication;

/**
 * @author luoyingxiong
 */
public class OAuth2Constant {

    /**
     * 图形验证码模式（自定义）
     */
    public static final String GRANT_TYPE_CAPTCHA = "captcha";

    /**
     * 密码模式（自定义）
     */
    public static final String GRANT_TYPE_PASSWORD = "authorization_password";

    /**
     * 登录地址
     */
    public static final String LOGIN_URL = "/login";

    private OAuth2Constant() {
    }
}
