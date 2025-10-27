package com.luoyx.hauyne.uaa.authentication.captcha;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luoyx.hauyne.framework.utils.rsa.RSAUtil;
import com.luoyx.hauyne.uaa.dto.CachedCaptchaDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.net.URLDecoder;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.luoyx.hauyne.uaa.controller.CaptchaController.REDIS_KEY_IMAGE_CODE;

@Slf4j
public class CaptchaGrantAuthenticationProvider implements AuthenticationProvider {

    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private RedisTemplate redisTemplate;
    private final OAuth2AuthorizationService auth2AuthorizationService;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    /**
     * ID_TOKEN_TOKEN_TYPE
     */
    private static final OAuth2TokenType ID_TOKEN_TOKEN_TYPE = new OAuth2TokenType(OidcParameterNames.ID_TOKEN);

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    /**
     * 是否检查用户输入的验证码【true=是，false=否；缺省值为true】
     * 设置这个开关，主要是为了在开发环境下跳过验证码的检查，提高测试效率。测试环境视情况而定。生产环境不要配置该项
     */
    @Value("${enable.captcha.check:true}")
    private boolean enableCaptchaCheck;

    public CaptchaGrantAuthenticationProvider(OAuth2AuthorizationService auth2AuthorizationService,
                                              OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        Assert.notNull(auth2AuthorizationService, "authorizationService cannot be null");
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
        this.auth2AuthorizationService = auth2AuthorizationService;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        CaptchaGrantAuthenticationToken captchaGrantAuthenticationToken = (CaptchaGrantAuthenticationToken) authentication;
        Map<String, Object> additionalParameters = captchaGrantAuthenticationToken.getAdditionalParameters();

        // 授权类型
        AuthorizationGrantType authorizationGrantType = captchaGrantAuthenticationToken.getGrantType();

        // 用户名、密码
        String username = (String) additionalParameters.get(OAuth2ParameterNames.USERNAME);
        String password = (String) additionalParameters.get(OAuth2ParameterNames.PASSWORD);

        log.info("enableCaptchaCheck => {}", enableCaptchaCheck);

        String requestImgCode = (String) additionalParameters.get("captcha");
        String captchaKey = (String) additionalParameters.get("captchaKey");

        if (StringUtils.isBlank(requestImgCode)) {
            log.warn("验证码不能为空");
            throw new BadCredentialsException("验证码不能为空");
        }
        Object redisImgCodeObj = redisTemplate.opsForValue().get(REDIS_KEY_IMAGE_CODE + captchaKey);
        if (null == redisImgCodeObj) {
            log.warn("验证码已过期");
            throw new BadCredentialsException("验证码已过期");
        }
        CachedCaptchaDTO cachedCaptchaDTO = null;
        try {
            cachedCaptchaDTO = new ObjectMapper().readValue(redisImgCodeObj.toString(), CachedCaptchaDTO.class);

            // 删除验证码
            redisTemplate.delete(REDIS_KEY_IMAGE_CODE + captchaKey);
            if (enableCaptchaCheck && !cachedCaptchaDTO.getActualImgCode().equalsIgnoreCase(requestImgCode)) {
                log.warn("验证码不正确");
                throw new BadCredentialsException("验证码不正确");
            }
            String base64Password = password;

            //URL decode(前端发过来的是RSA公钥加密后的base64格式密文，urlencode发送到服务器，斜杠/、加号+会被urlencode)
            String rsaEncryptPassword = URLDecoder.decode(base64Password, "utf-8");
            String privateKey = cachedCaptchaDTO.getRsaPrivateKey();
            // RSA 私钥解密
            password = RSAUtil.decryptByPrivateKey(rsaEncryptPassword, privateKey);
            // 设置解密后的密码【此时已是明文】
        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            log.warn("账号不存在");
            throw new InternalAuthenticationServiceException("账号不存在");
        }

        if (!userDetails.isEnabled()) {
            throw new DisabledException("你已被禁用");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            log.warn("密码错误");
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "密码错误"));
        }

        //请求参数权限范围
        String requestScopesStr = (String) additionalParameters.get(OAuth2ParameterNames.SCOPE);
        //请求参数权限范围专场集合
        Set<String> requestScopeSet = Collections.emptySet();
        if (StringUtils.isNotBlank(requestScopesStr)) {
            requestScopeSet = Stream.of(requestScopesStr.split(" ")).collect(Collectors.toSet());
        }

        // Ensure then client is authenticated
        OAuth2ClientAuthenticationToken clientPrincipal =
                getAuthenticatedClientElseThrowInvalidClient(captchaGrantAuthenticationToken);
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();

        // Ensure the client is configured to use this authorization grant type
        if (!registeredClient.getAuthorizationGrantTypes().contains(authorizationGrantType)) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
        }


        // 由于在上面已验证过用户名、密码，现在构建一个已认证的对象UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken
                .authenticated(userDetails, clientPrincipal, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(captchaGrantAuthenticationToken.getDetails());

        // Initialize the DefaultOAuth2TokenContext
        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(usernamePasswordAuthenticationToken)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorizationGrantType(authorizationGrantType)
                .authorizedScopes(requestScopeSet)
                .authorizationGrant(captchaGrantAuthenticationToken);

        // Initialize the OAuth2Authorization
        OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.withRegisteredClient(registeredClient)
                .principalName(clientPrincipal.getName())
                .authorizedScopes(requestScopeSet)
                .attribute(Principal.class.getName(), usernamePasswordAuthenticationToken)
                .authorizationGrantType(authorizationGrantType);

        // -------- Access token -------
        OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
        OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
        if (generatedAccessToken == null) {
            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                    "The token generator failed to generate the access token", ERROR_URI);
            throw new OAuth2AuthenticationException(error);
        }
        if (log.isTraceEnabled()) {
            log.trace("Generated access token");
        }
        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                generatedAccessToken.getTokenValue(),
                generatedAccessToken.getIssuedAt(),
                generatedAccessToken.getExpiresAt(),
                tokenContext.getAuthorizedScopes()
        );

        if (generatedAccessToken instanceof ClaimAccessor) {
            authorizationBuilder.token(accessToken, (metadata) ->
                    metadata.put(
                            OAuth2Authorization.Token.CLAIMS_METADATA_NAME,
                            ((ClaimAccessor) generatedAccessToken).getClaims()
                    )
            );
        } else {
            authorizationBuilder.accessToken(accessToken);
        }

        // ----- Refresh token -----
        OAuth2RefreshToken refreshToken = null;
        if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN)

                // Do not issue refresh token to public client
                && !clientPrincipal.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.NONE)) {
            tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
            OAuth2Token generatedRefreshToken = this.tokenGenerator.generate(tokenContext);
            if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
                OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                        "The token generator failed to generate the refresh token.",
                        ERROR_URI
                );
                throw new OAuth2AuthenticationException(error);
            }
            if (log.isTraceEnabled()) {
                log.trace("Generated refresh token");
            }

            refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
            authorizationBuilder.refreshToken(refreshToken);
        }

        // 保存认证信息
        OAuth2Authorization authorization = authorizationBuilder.build();
        this.auth2AuthorizationService.save(authorization);
        if (log.isTraceEnabled()) {
            log.trace("Saved authorization");
        }

        if (log.isTraceEnabled()) {
            log.trace("Authenticated token request");
        }

        return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken, refreshToken, additionalParameters);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CaptchaGrantAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private static OAuth2ClientAuthenticationToken getAuthenticatedClientElseThrowInvalidClient(Authentication authentication) {
        OAuth2ClientAuthenticationToken clientPrincipal = null;
        if (OAuth2ClientAuthenticationToken.class.isAssignableFrom(authentication.getPrincipal().getClass())) {
            clientPrincipal = (OAuth2ClientAuthenticationToken) authentication.getPrincipal();
        }
        if (clientPrincipal != null && clientPrincipal.isAuthenticated()) {
            return clientPrincipal;
        }
        throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
    }

    /**
     * @param set1
     * @param set2
     * @return
     * @author Rommel
     * @date 2023/7/21-3:11
     * @version 1.0
     * @description 取两个集合的交集
     */
    private Set<String> getInterseSet(Set<String> set1, Set<String> set2) {
        if (CollectionUtils.isEmpty(set1) || CollectionUtils.isEmpty(set2)) {
            return Set.of();
        }
        Set<String> set = set1.stream().filter(set2::contains).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(set)) {
            set = Set.of();
        }
        return set;
    }
}

/**
 * 自定义Provider
 */
//@Slf4j
//@RequiredArgsConstructor
//public class CaptchaGrantAuthenticationProvider implements AuthenticationProvider {
//
//    private final UserDetailsService myUserDetailsService;
//    private final PasswordEncoder passwordEncoder;
//    private final RedisTemplate redisTemplate;
//
//    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
//
//    /**
//     * 是否检查用户输入的验证码【true=是，false=否；缺省值为true】
//     * 设置这个开关，主要是为了在开发环境下跳过验证码的检查，提高测试效率。测试环境视情况而定。生产环境不要配置该项
//     */
//    @Value("${enable.captcha.check:true}")
//    private boolean enableCaptchaCheck;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) {
//        CaptchaGrantAuthenticationToken authenticationToken = (CaptchaGrantAuthenticationToken) authentication;
//        validate(authenticationToken);
//        String username = (String) authenticationToken.getPrincipal();
//        String password = (String) authenticationToken.getCredentials();
//
//        UserDetails user = myUserDetailsService.loadUserByUsername(username);
//        if (user == null) {
//            log.warn("账号不存在");
//            throw new InternalAuthenticationServiceException("账号不存在");
//        }
//
//        if (!user.isEnabled()) {
//            throw new DisabledException("你已被禁用");
//        }
//
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            log.warn("密码错误");
//            throw new BadCredentialsException(messages.getMessage(
//                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
//                    "密码错误"));
//        }
//        CaptchaGrantAuthenticationToken authenticationResult = new CaptchaGrantAuthenticationToken(user, password, user.getAuthorities());
//        authenticationResult.setDetails(authenticationToken.getDetails());
//
//        return authenticationResult;
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return CaptchaGrantAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//
//    private void validate(CaptchaGrantAuthenticationToken authenticationToken) throws BadCredentialsException {
//        log.info("enableCaptchaCheck => {}", enableCaptchaCheck);
//        String requestImgCode = authenticationToken.getCaptcha();
//        String captchaKey = authenticationToken.getCaptchaKey();
//
//        if (StringUtils.isBlank(requestImgCode)) {
//            log.warn("验证码不能为空");
////            throw new BadCredentialsException(JsonUtil.toString(APIError.invalidParam("验证码不能为空")));
//            throw new BadCredentialsException("验证码不能为空");
//        }
//        Object redisImgCodeObj = redisTemplate.opsForValue().get(REDIS_KEY_IMAGE_CODE + captchaKey);
//        if (null == redisImgCodeObj) {
//            log.warn("验证码已过期");
////            throw new BadCredentialsException(JsonUtil.toString(APIError.invalidParam("验证码已过期")));
//            throw new BadCredentialsException("验证码已过期");
//        }
//        CachedCaptchaDTO cachedCaptchaDTO = null;
//        try {
//            cachedCaptchaDTO = new ObjectMapper().readValue(redisImgCodeObj.toString(), CachedCaptchaDTO.class);
//
//            //删除验证码
//            redisTemplate.delete(REDIS_KEY_IMAGE_CODE + captchaKey);
//            if (!cachedCaptchaDTO.getActualImgCode().equalsIgnoreCase(requestImgCode) && enableCaptchaCheck) {
//                log.warn("验证码不正确");
//                throw new BadCredentialsException("验证码不正确");
//            }
//            String base64Password = (String) authenticationToken.getCredentials();
//
//            //URL decode(前端发过来的是RSA公钥加密后的base64格式密文，urlencode发送到服务器，斜杠/、加号+会被urlencode)
//            String rsaEncryptPassword = URLDecoder.decode(base64Password, "utf-8");
//            String privateKey = cachedCaptchaDTO.getRsaPrivateKey();
//            // RSA 私钥解密
//            String password = RSAUtil.decryptByPrivateKey(rsaEncryptPassword, privateKey);
//            //设置解密后的密码【此时已是明文】
//            authenticationToken.setCredentials(password);
//        } catch (Exception e) {
//            throw new BadCredentialsException(e.getMessage());
//        }
//    }
//}
