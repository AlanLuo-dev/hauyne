package com.luoyx.hauyne.uaa.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bingoohuang.patchca.background.MyCustomBackgroundFactory;
import com.github.bingoohuang.patchca.custom.ConfigurableCaptchaService;
import com.github.bingoohuang.patchca.filter.predefined.DoubleRippleFilterFactory;
import com.github.bingoohuang.patchca.word.RandomWordFactory;
import com.luoyx.hauyne.security.autoconfigure.MyAuthenticationEntryPoint;
import com.luoyx.hauyne.security.pojo.CurrentSysUser;
import com.luoyx.hauyne.security.pojo.CurrentSysUserMixin;
import com.luoyx.hauyne.uaa.authentication.captcha.CaptchaGrantAuthenticationConverter;
import com.luoyx.hauyne.uaa.authentication.captcha.CaptchaGrantAuthenticationProvider;
import com.luoyx.hauyne.uaa.authentication.password.PasswordGrantAuthenticationConverter;
import com.luoyx.hauyne.uaa.authentication.password.PasswordGrantAuthenticationProvider;
import com.luoyx.hauyne.uaa.filter.CookieRefreshTokenFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2AccessTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsSet;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;

import java.awt.*;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 授权服务器配置
 *
 * @author zt21961
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class AuthorizationServerConfig {

    private static final String CUSTOM_CONSENT_PAGE_URI = "/oauth2/consent";

    @Bean
    @Order(1)
//    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http,
                                                                      OAuth2AuthorizationService authorizationService,
                                                                      OAuth2TokenGenerator<?> tokenGenerator,
                                                                      CookieTokenResponseHandler tokenResponseHandler)
            throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)

                // 设置自定义的图形验证码模式
                .tokenEndpoint(tokenEndpoint -> tokenEndpoint
                        .accessTokenRequestConverter(new CaptchaGrantAuthenticationConverter())
                        .authenticationProvider(
                                new CaptchaGrantAuthenticationProvider(
                                        authorizationService,
                                        tokenGenerator
                                )
                        )
                        .accessTokenResponseHandler(tokenResponseHandler)
                        .errorResponseHandler(new CustomAuthExceptionHandler())
                )

                //设置自定义密码模式
                .tokenEndpoint(tokenEndpoint ->
                        tokenEndpoint
                                .accessTokenRequestConverter(new PasswordGrantAuthenticationConverter())
                                .authenticationProvider(
                                        new PasswordGrantAuthenticationProvider(authorizationService, tokenGenerator)
                                )
                )

                // 开启OpenID Connect 1.0 （其中oidc为 OpenID Connect 的缩写）。
                .oidc(oidc ->
                        // 拉取用户信息时，映射权限到声明属性中
                        oidc.userInfoEndpoint(user -> user.userInfoMapper(userInfoMapper()))
                )   // Enable OpenID Connect 1.0
                .tokenRevocationEndpoint(tokenRevocationEndpoint ->
                        tokenRevocationEndpoint
                                .revocationRequestConverter(new CookieOAuth2TokenRevocationAuthenticationConverter())
                                .revocationResponseHandler(
                                        new ClearCookieOnRevokeSuccessHandler(new String[]{
                                                OAuth2ParameterNames.ACCESS_TOKEN,
                                                OAuth2ParameterNames.REFRESH_TOKEN}
                                        )
                                )
                );


        log.info("设置异常处理");
        http
                // 设置异常处理
                .addFilterBefore(new MyExceptionTranslationFilter(), ExceptionTranslationFilter.class)
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(new MyAuthenticationEntryPoint())
                );

        // 开启cors, 放行前端地址
        http.cors(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public FilterRegistrationBean<CookieRefreshTokenFilter> refreshTokenFilterRegistration() {
        FilterRegistrationBean<CookieRefreshTokenFilter> reg = new FilterRegistrationBean<>();
        reg.setFilter(new CookieRefreshTokenFilter());
        reg.addUrlPatterns("/oauth2/token"); // 只拦截 token 请求
        reg.setOrder(Ordered.HIGHEST_PRECEDENCE); // 在 springSecurityFilterChain 之前执行
        return reg;
    }

    private static final List<String> EMAIL_CLAIMS = Arrays.asList(
            StandardClaimNames.EMAIL,
            StandardClaimNames.EMAIL_VERIFIED
    );
    private static final List<String> PHONE_CLAIMS = Arrays.asList(
            StandardClaimNames.PHONE_NUMBER,
            StandardClaimNames.PHONE_NUMBER_VERIFIED
    );
    private static final List<String> PROFILE_CLAIMS = Arrays.asList(
            StandardClaimNames.NAME,
            StandardClaimNames.FAMILY_NAME,
            StandardClaimNames.GIVEN_NAME,
            StandardClaimNames.MIDDLE_NAME,
            StandardClaimNames.NICKNAME,
            StandardClaimNames.PREFERRED_USERNAME,
            StandardClaimNames.PROFILE,
            StandardClaimNames.PICTURE,
            StandardClaimNames.WEBSITE,
            StandardClaimNames.GENDER,
            StandardClaimNames.BIRTHDATE,
            StandardClaimNames.ZONEINFO,
            StandardClaimNames.LOCALE,
            StandardClaimNames.UPDATED_AT
    );

    /**
     * 在获取访问令牌后，自动请求用户端点时应用该Bean。
     * 当前网关客户端中，未使用访问令牌中的声明，而是使用访问令牌的断点
     *
     * @see org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationProvider.userInfoMapper
     * {@link https://docs.spring.io/spring-authorization-server/reference/protocol-endpoints.html#oidc-user-info-endpoint}
     */
    Function<OidcUserInfoAuthenticationContext, OidcUserInfo> userInfoMapper() {
        return authenticationContext -> {
            OAuth2Authorization authorization = authenticationContext.getAuthorization();
            OAuth2AccessToken accessToken = authenticationContext.getAccessToken();
            Map<String, Object> scopeRequestedClaims = getClaimsRequestedByScope(authorization,
                    accessToken.getScopes());
            return new OidcUserInfo(scopeRequestedClaims);
        };
    }

    private static Map<String, Object> getClaimsRequestedByScope(OAuth2Authorization authorization,
                                                                 Set<String> requestedScopes) {
        Map<String, Object> claims = authorization.getToken(OidcIdToken.class).getToken().getClaims();

        Set<String> scopeRequestedClaimNames = new HashSet<>(32);
        scopeRequestedClaimNames.add(StandardClaimNames.SUB);

        if (requestedScopes.contains(OidcScopes.ADDRESS)) {
            scopeRequestedClaimNames.add(StandardClaimNames.ADDRESS);
        }
        if (requestedScopes.contains(OidcScopes.EMAIL)) {
            scopeRequestedClaimNames.addAll(EMAIL_CLAIMS);
        }
        if (requestedScopes.contains(OidcScopes.PHONE)) {
            scopeRequestedClaimNames.addAll(PHONE_CLAIMS);
        }
        if (requestedScopes.contains(OidcScopes.PROFILE)) {
            scopeRequestedClaimNames.addAll(PROFILE_CLAIMS);
        }

        Map<String, Object> requestedClaims = new HashMap<>(claims);
        requestedClaims.keySet().removeIf((claimName) -> !scopeRequestedClaimNames.contains(claimName));

        // 将用户权限添加到声明上
        Principal principal = authorization.getAttribute(Principal.class.getName());
        if (principal instanceof Authentication) {
            Authentication auth = (Authentication) principal;
            Set<String> roles = auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .map(c -> c.replaceFirst("^ROLE_", ""))
                    .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
            requestedClaims.put("roles", roles);
        }
        return requestedClaims;
    }

//    /**
//     * 客户端信息
//     * 对应表 oauth2_registered_client
//     *
//     * @param jdbcTemplate
//     * @return
//     */
//    @Bean
//    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
//        return new JdbcRegisteredClientRepository(jdbcTemplate);
//    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        JdbcRegisteredClientRepository jdbcRegisteredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);

        RegisteredClient registeredClient = jdbcRegisteredClientRepository.findByClientId("service-admin"); // password-client-id
        TokenSettings tokenSettings = registeredClient.getTokenSettings();
        if (tokenSettings.getSetting("x509CertificateBoundAccessTokens") == null) {
            TokenSettings finalTokenSettings = tokenSettings;
            tokenSettings = TokenSettings.builder()
                    .settings(settings -> settings.putAll(finalTokenSettings.getSettings()))
                    .x509CertificateBoundAccessTokens(Boolean.FALSE)
                    .build();

            registeredClient = RegisteredClient.from(registeredClient)
                    .tokenSettings(tokenSettings)
                    .build();

            jdbcRegisteredClientRepository.save(registeredClient);
        }


        return jdbcRegisteredClientRepository;
    }

    /**
     * 授权信息
     * 对应表：oauth2_authorization
     */
    @Bean
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate,
                                                           RegisteredClientRepository registeredClientRepository) {
        JdbcOAuth2AuthorizationService service = new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
        JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper authorizationRowMapper
                = new JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper(
                registeredClientRepository
        );
        authorizationRowMapper.setLobHandler(new DefaultLobHandler());

        ObjectMapper objectMapper = new ObjectMapper();
        ClassLoader classLoader = JdbcOAuth2AuthorizationService.class.getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        objectMapper.registerModules(securityModules);
        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
        objectMapper.addMixIn(CurrentSysUser.class, CurrentSysUserMixin.class);
        authorizationRowMapper.setObjectMapper(objectMapper);

        service.setAuthorizationRowMapper(authorizationRowMapper);

        return service;
    }

    /**
     * 授权确认
     * 对应表：oauth2_authorization_consent
     */
    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate,
                                                                         RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置认证服务器请求地址
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        //什么都不配置，则使用默认地址
        return AuthorizationServerSettings.builder().build();
    }

    /**
     * 配置token生成器
     *
     * @return 令牌生成器
     */
    @Bean
    OAuth2TokenGenerator<OAuth2Token> tokenGenerator() {
        OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
        accessTokenGenerator.setAccessTokenCustomizer(opaqueTokenCustomizer());
        OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();

        return new DelegatingOAuth2TokenGenerator(accessTokenGenerator, refreshTokenGenerator);
    }

    /**
     * 自定义 令牌填充
     *
     * @return
     */
    @Bean
    public OAuth2TokenCustomizer<OAuth2TokenClaimsContext> opaqueTokenCustomizer() {
        return context -> {
            if (context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)) {
                Authentication principal = context.getPrincipal();
                if (principal != null && principal.getPrincipal() != null) {
                    // 写入到 access token 的 claims 中
                    CurrentSysUser currentSysUser = (CurrentSysUser) principal.getPrincipal();
                    OAuth2TokenClaimsSet.Builder claims = context.getClaims();
                    claims.claim("principal", currentSysUser);

                    claims.claims(claimsMap -> {
                        // 获取当前已有的 scope（如果没有就初始化一个空集合）
                        Set<String> scopes = new HashSet<>(
                                Optional.ofNullable((Collection<String>) claimsMap.get("scope")).orElse(Collections.emptySet())
                        );

                        // 将当前用户的权限添加到 scopes 中
                        for (GrantedAuthority authority : currentSysUser.getAuthorities()) {
                            scopes.add(authority.getAuthority());
                        }

                        // 放回 claims 中
                        claimsMap.put("scope", scopes);
                    });

                }
            }
        };
    }

//    @Bean
//    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
//        return context -> {
//            JwsHeader.Builder headers = context.getJwsHeader();
//            JwtClaimsSet.Builder claims = context.getClaims();
//            UserDetails userDetails = userDetailsService.loadUserByUsername(context.getPrincipal().getName());
//            if (context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)) {
//                //
//
//                /**
//                 * Customize headers/claims for access_token
//                 * 客户端权限 + 加上用户的Spring Security权限，都合并到scope中【体现到JWT则是在PAYLOAD的scope中】
//                 */
////                claims.claims(claimsConsumer->{
////                    claimsConsumer.merge("scope", userDetails.getAuthorities(), (scope, authorities) -> {
////                        Set<String> scopeSet = (Set<String>) scope;
////                        Collection<SimpleGrantedAuthority> simpleGrantedAuthorities = (Collection<SimpleGrantedAuthority>) authorities;
////                        simpleGrantedAuthorities.forEach(simpleGrantedAuthority -> {
////                            if (!scopeSet.contains(simpleGrantedAuthority.getAuthority())) {
////                                scopeSet.add(simpleGrantedAuthority.getAuthority());
////                            }
////                        });
////                        return scopeSet;
////                    });
////                });
//
//                List<String> auths = new ArrayList<>();
//                Authentication principal = context.getPrincipal();
//                System.out.println("类名：" + principal.getClass().getName());
//                for (GrantedAuthority auth : context.getPrincipal().getAuthorities()) {
//                    auths.add(auth.getAuthority());
//                }
//                claims.claim("authorities", auths);
//                if (userDetails instanceof CurrentSysUser currentSysUser) {
//                    claims.claim("menus", JsonUtil.toString(currentSysUser.getMenus()));
//                    claims.claim("userId", String.valueOf(currentSysUser.getId()));
//                }
//            } else if (context.getTokenType().getValue().equals(OidcParameterNames.ID_TOKEN)) {
//
//                // Customize headers/claims for id_token
//                claims.claim(IdTokenClaimNames.AUTH_TIME, Date.from(Instant.now()));
//                SessionInformation sessionInformation = context.get(SessionInformation.class);
//                claims.claim("sid", sessionInformation.getSessionId());
//                claims.claim("username", userDetails.getUsername());
//                claims.claim("name", "测试name");
//                claims.claim("description", "测试description");
//            }
//        };
//    }

    /**
     * 图形验证码配置
     *
     * @return
     */
    @Bean
    public ConfigurableCaptchaService captchaService() {
        ConfigurableCaptchaService captchaService = new ConfigurableCaptchaService();
        Random random = new Random();
        captchaService.setColorFactory((x) -> {
            int[] c = new int[3];
            int i = random.nextInt(c.length);
            for (int fi = 0; fi < c.length; fi++) {
                if (fi == i) {
                    c[fi] = random.nextInt(71);
                } else {
                    c[fi] = random.nextInt(256);
                }
            }
            return new Color(c[0], c[1], c[2]);
        });
        RandomWordFactory wf = new RandomWordFactory();
        wf.setCharacters("23456789abcdefghigkmnpqrstuvwxyzABCDEFGHIGKLMNPQRSTUVWXYZ");
        wf.setMaxLength(4);
        wf.setMinLength(4);
        captchaService.setWordFactory(wf);
        captchaService.setBackgroundFactory(new MyCustomBackgroundFactory());
        captchaService.setFilterFactory(new DoubleRippleFilterFactory());

        return captchaService;
    }
}
