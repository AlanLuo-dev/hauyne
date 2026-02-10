//package com.luoyx.hauyne.uaa.config;
//
//import com.luoyx.hauyne.uaa.config.translator.AuthWebResponseExceptionTranslator;
//import com.luoyx.hauyne.uaa.filter.RefreshTokenFilter;
//import com.luoyx.hauyne.uaa.authentication.granter.CaptchaTokenGranter;
//import com.luoyx.hauyne.uaa.authentication.granter.CustomRefreshTokenGranter;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.ClientDetailsService;
//import org.springframework.security.oauth2.provider.CompositeTokenGranter;
//import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
//import org.springframework.security.oauth2.provider.TokenGranter;
//import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
//import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
//import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
//import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
//import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
//import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
//
//import javax.sql.DataSource;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author 罗英雄
// * @date 2020/5/10 21:53
// * @EnableAuthorizationServer //开启认证服务器
// */
//@Slf4j
//@Configuration
//@EnableAuthorizationServer
//@RequiredArgsConstructor
//public class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
//
//    @Qualifier("dataSource")
//    private final DataSource dataSource;
//    private final RedisConnectionFactory redisConnectionFactory;
//    private final PasswordEncoder bcryptPasswordEncoder;
//    private final AuthenticationManager authenticationManagerBean;
//    private final UserDetailsService myUserDetailsService;
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        log.info("执行 ==> AuthorizationServerConfigurerAdapter 配置");
//        endpoints
//                // 要配置accessTokenConverter才能正确生成JwtToken，否则光配置tokenStore生成的依然普通的access_token。
////                .accessTokenConverter(jwtTokenEnhancer())
//                .tokenStore(redisTokenStore())
//
//                .userDetailsService(myUserDetailsService)
//
//                // 配置authenticationManager才会开启密码类型的验证。
//                .authenticationManager(authenticationManagerBean)
//                .tokenGranter(tokenGranter(endpoints))
//                .exceptionTranslator(new AuthWebResponseExceptionTranslator())
//
//                /**
//                 * refresh_token 有两种使用方式：重复使用（true）、非重复使用（false），默认为true
//                 * 1、重复使用：access_token过期刷新时，refresh_token过期时间未改变，仍以初次生成的时间为准
//                 * 2、非重复使用：access_token过期刷新时，refresh_token过期时延续，在refresh_token有效期内刷新便永不失效达到无需再次登录的目的
//                 */
//                .reuseRefreshTokens(true);
//    }
//
//    /**
//     * 使用Redis存储accessToken
//     *
//     * @return
//     */
//    @Bean
//    public TokenStore redisTokenStore() {
//        return new RedisTokenStore(redisConnectionFactory);
//    }
//
//    private TokenGranter tokenGranter(final AuthorizationServerEndpointsConfigurer endpoints) {
//        List<TokenGranter> granters = new ArrayList<>();
//        AuthorizationServerTokenServices tokenServices = endpoints.getTokenServices();
//        ClientDetailsService clientDetailsService = endpoints.getClientDetailsService();
//        OAuth2RequestFactory oAuth2RequestFactory = endpoints.getOAuth2RequestFactory();
//
//        granters.add(new AuthorizationCodeTokenGranter(tokenServices, endpoints.getAuthorizationCodeServices(), clientDetailsService, oAuth2RequestFactory));
//        granters.add(new CustomRefreshTokenGranter(tokenServices, clientDetailsService, oAuth2RequestFactory));
//        granters.add(new ImplicitTokenGranter(tokenServices, clientDetailsService, oAuth2RequestFactory));
//        granters.add(new ClientCredentialsTokenGranter(tokenServices, clientDetailsService, oAuth2RequestFactory));
//        granters.add(new ResourceOwnerPasswordTokenGranter(authenticationManagerBean, tokenServices, clientDetailsService, oAuth2RequestFactory));
//        granters.add(new CaptchaTokenGranter(authenticationManagerBean, tokenServices, clientDetailsService, oAuth2RequestFactory));
//
//        return new CompositeTokenGranter(granters);
//    }
//
//    @Bean
//    public JdbcClientDetailsService clientDetails() {
//        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
//        clientDetailsService.setPasswordEncoder(bcryptPasswordEncoder);
//
//        return new JdbcClientDetailsService(dataSource);
//    }
//
//    /**
//     * 配置客户端的基本信息
//     *
//     * @param clients
//     * @throws Exception
//     */
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.withClientDetails(clientDetails());
//    }
//
//    /**
//     * 配置获取 Token的策略
//     *
//     * @param oauthServer
//     * @throws Exception
//     */
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
//
//        // 添加refresh_token 过滤器
//        oauthServer.addTokenEndpointAuthenticationFilter(new RefreshTokenFilter());
//
//        /**
//         * 允许表单认证，开启后，访问获取token的接口oauth/token时，client_id和client_secret可以以表单参数的形式发送
//         * 如果不开启此项，则默认为Http basic验证, 需以请求头参数的形式传参，参数名：Authorization； 参数值格式为：base64(client_id:client_secret)
//         */
////        oauthServer.allowFormAuthenticationForClients();
//
//        //允许已授权用户访问 获取 token 接口和checkToken 接口。
//        oauthServer
//
//                // 开启端口/oauth/token_key的访问权限（允许）
//                .tokenKeyAccess("permitAll()")
//
//                // 开启端口/oauth/check_token的访问权限（允许）
//                .checkTokenAccess("permitAll()");
//    }
//}