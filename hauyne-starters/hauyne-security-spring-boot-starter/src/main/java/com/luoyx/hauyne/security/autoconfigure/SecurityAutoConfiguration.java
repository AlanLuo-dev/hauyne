package com.luoyx.hauyne.security.autoconfigure;

import com.luoyx.hauyne.security.filter.UserContextFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

@Slf4j
@Configuration
public class SecurityAutoConfiguration {

    //    @Autowired
//    @Qualifier("handlerExceptionResolver")
//    private HandlerExceptionResolver resolver;
    @Bean
    public HandlerExceptionResolver resolver() {
        return new ExceptionHandlerExceptionResolver();
    }

//    @Bean
//    public BearerTokenExtractor cookieTokenExtractor() {
//        log.info("OAuth2.0配置: 创建Bean -> {}", CookieTokenExtractor.class);
//        return new CookieTokenExtractor();
//    }
//
//    @Bean
//    public PrincipalExtractor principalExtractor() {
//        log.info("OAuth2.0配置: 创建Bean -> {}", OAuth2PrincipalExtractor.class);
//        return new OAuth2PrincipalExtractor();
//    }

    /**
     * 设置SecurityContextHolder的策略为SecurityContextHolder.MODE_INHERITABLETHREADLOCAL。
     * <p>
     * 用于在线程之间继承安全上下文。子线程可以继承父线程的安全上下文，从而在异步任务或线程池中也能够访问到正确的安全上下文信息。
     * <p>
     * a、只有在框架本身创建线程时（例如@Async）,该配置才生效，
     * b、不适用于框架以外的创建线程的方式
     *
     * @return
     */
//    @Bean
//    public InitializingBean initializingBean() {
//        log.info("Spring Security安全上下文管理策略设置为 -> {}", SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
//        return () -> SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
//    }
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        AuthenticationEntryPoint authenticationEntryPoint = new DelegatedAuthenticationEntryPoint(resolver());
        log.info("创建Bean DelegatedAuthenticationEntryPoint");

        return authenticationEntryPoint;
    }

    @Bean
    public UserContextFilter userContextFilter() {
        UserContextFilter userContextFilter = new UserContextFilter();
        log.info("已创建Bean UserContextFilter");

        return userContextFilter;
    }

//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new CustomJwtAuthenticationConverter());
//        return jwtAuthenticationConverter;
//    }

    @Bean
    public UserContextFilterConfigurer userContextFilterConfigurer() {
        return new UserContextFilterConfigurer(userContextFilter());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   BearerTokenResolver cookieBearerTokenResolver,
                                                   OpaqueTokenIntrospector opaqueTokenIntrospector) throws Exception {
        log.info("security公共模块  执行资源服务器配置");
        return http
                .authorizeHttpRequests(authorize -> authorize

                        // 所有的访问都需要通过身份认证
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .opaqueToken(opaqueTokenConfigurer ->
                                opaqueTokenConfigurer.introspector(opaqueTokenIntrospector)
                        )
                        .bearerTokenResolver(cookieBearerTokenResolver)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(ex -> {
                    ex.authenticationEntryPoint(authenticationEntryPoint());
                })
                .build();
    }

    @Bean
    public OpaqueTokenIntrospector opaqueTokenIntrospector(){
        return new CustomOpaqueTokenIntrospector("http://localhost:8762/oauth2/introspect",
                "service-admin", "123456");
    }

    /**
     * 从Cookie中提取访问令牌
     *
     * @return BearerTokenResolver
     */
    @Bean
    public BearerTokenResolver cookieBearerTokenResolver() {
        return new CookieBearerTokenResolver();
    }
}
