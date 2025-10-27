//package com.luoyx.hauyne.uaa.authentication.password_backup;
//
//import jakarta.annotation.Resource;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.core.*;
//import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
//import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
//import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
//import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
//import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
//import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
//import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
//import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
//import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
//import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
//import org.springframework.util.Assert;
//
//import java.security.Principal;
//import java.util.Map;
//import java.util.Set;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
///**
// * @author Rommel
// * @version 1.0
// * @date 2023/7/20-14:41
// * @description TODO
// */
//@Slf4j
//public class PasswordGrantAuthenticationProvider implements AuthenticationProvider {
//
//    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";
//
//    @Resource
//    private UserDetailsService userDetailsService;
//    @Resource
//    private PasswordEncoder passwordEncoder;
//
//    private final OAuth2AuthorizationService authorizationService;
//    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;
//
//    public PasswordGrantAuthenticationProvider(OAuth2AuthorizationService authorizationService,
//                                               OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
//        Assert.notNull(authorizationService, "authorizationService cannot be null");
//        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
//        this.authorizationService = authorizationService;
//        this.tokenGenerator = tokenGenerator;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        PasswordGrantAuthenticationToken passwordGrantAuthenticationToken =
//                (PasswordGrantAuthenticationToken) authentication;
//
//        Map<String, Object> additionalParameters = passwordGrantAuthenticationToken.getAdditionalParameters();
//
//        //授权类型
//        AuthorizationGrantType authorizationGrantType = passwordGrantAuthenticationToken.getGrantType();
//        //用户名
//        String username = (String)additionalParameters.get(OAuth2ParameterNames.USERNAME);
//        //密码
//        String password = (String)additionalParameters.get(OAuth2ParameterNames.PASSWORD);
//
//        //请求参数权限范围
//        String requestScopesStr = (String)additionalParameters.get(OAuth2ParameterNames.SCOPE);
//        //请求参数权限范围专场集合
//        Set<String> requestScopeSet = Stream.of(requestScopesStr.split(" ")).collect(Collectors.toSet());
//
//        // Ensure the client is authenticated
//        OAuth2ClientAuthenticationToken clientPrincipal =
//                getAuthenticatedClientElseThrowInvalidClient(passwordGrantAuthenticationToken);
//        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
//
//        // Ensure the client is configured to use this authorization grant type
//        if (!registeredClient.getAuthorizationGrantTypes().contains(authorizationGrantType)) {
//            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
//        }
//
//        //校验用户名信息
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        if(!passwordEncoder.matches(password,userDetails.getPassword())){
//            throw new OAuth2AuthenticationException("密码不正确！");
//        }
//
//        // 由于在上面已验证过用户名、密码，现在构建一个已认证的对象UsernamePasswordAuthenticationToken
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken
//                .authenticated(userDetails, clientPrincipal, userDetails.getAuthorities());
//
//
//
//
//        // Generate the access token
//        OAuth2TokenContext tokenContext = DefaultOAuth2TokenContext.builder()
//                .registeredClient(registeredClient)
//                .principal(usernamePasswordAuthenticationToken)
//                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
//                .tokenType(OAuth2TokenType.ACCESS_TOKEN)
//                .authorizationGrantType(authorizationGrantType)
//                .authorizedScopes(requestScopeSet)
//                .authorizationGrant(passwordGrantAuthenticationToken)
//                .build();
//
//        OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
//        if (generatedAccessToken == null) {
//            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
//                    "The token generator failed to generate the access token.", null);
//            throw new OAuth2AuthenticationException(error);
//        }
//        log.trace("Generated access token");
//        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
//                generatedAccessToken.getTokenValue(),
//                generatedAccessToken.getIssuedAt(),
//                generatedAccessToken.getExpiresAt(),
//                tokenContext.getAuthorizedScopes()
//        );
//
//        // Initialize the OAuth2Authorization
//        OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.withRegisteredClient(registeredClient)
//                .principalName(clientPrincipal.getName())
//                .authorizedScopes(requestScopeSet)
//                .attribute(Principal.class.getName(), usernamePasswordAuthenticationToken)
//                .authorizationGrantType(authorizationGrantType);
//
//        if (generatedAccessToken instanceof ClaimAccessor) {
//            authorizationBuilder.token(accessToken, (metadata) ->
//                    metadata.put(
//                            OAuth2Authorization.Token.CLAIMS_METADATA_NAME,
//                            ((ClaimAccessor) generatedAccessToken).getClaims()
//                    )
//            );
//        } else {
//            authorizationBuilder.accessToken(accessToken);
//        }
//        // ----- Refresh token -----
//        OAuth2RefreshToken refreshToken = null;
//        if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN)
//
//                // Do not issue refresh token to public client
//                && !clientPrincipal.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.NONE)) {
//            tokenContext = DefaultOAuth2TokenContext.builder().tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
//            OAuth2Token generatedRefreshToken = this.tokenGenerator.generate(tokenContext);
//            if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
//                OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
//                        "The token generator failed to generate the refresh token.",
//                        ERROR_URI
//                );
//                throw new OAuth2AuthenticationException(error);
//            }
//                this.log.trace("Generated refresh token");
//
//            refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
//            authorizationBuilder.refreshToken(refreshToken);
//        }
//        OAuth2Authorization authorization = authorizationBuilder.build();
//
//        // Save the OAuth2Authorization
//        this.authorizationService.save(authorization);
//
//        return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken, refreshToken, additionalParameters);
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return PasswordGrantAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//
//    private static OAuth2ClientAuthenticationToken getAuthenticatedClientElseThrowInvalidClient(Authentication authentication) {
//        OAuth2ClientAuthenticationToken clientPrincipal = null;
//        if (OAuth2ClientAuthenticationToken.class.isAssignableFrom(authentication.getPrincipal().getClass())) {
//            clientPrincipal = (OAuth2ClientAuthenticationToken) authentication.getPrincipal();
//        }
//        if (clientPrincipal != null && clientPrincipal.isAuthenticated()) {
//            return clientPrincipal;
//        }
//        throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
//    }
//
//}
