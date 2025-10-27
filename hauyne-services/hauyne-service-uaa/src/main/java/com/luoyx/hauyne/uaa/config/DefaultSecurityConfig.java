package com.luoyx.hauyne.uaa.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @author luoyingxiong
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class DefaultSecurityConfig {

    private final AccessDeniedHandler accessDeniedHandler;

//    private final JwtDecoder jwtDecoder;

    /**
     * Spring Security 过滤器链配置（此处是纯Spring Security相关配置）
     *
     * @param http
     * @return
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/assets/**", "/webjars/**", "/login").permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandlingCustomizer ->
                        exceptionHandlingCustomizer.accessDeniedHandler(accessDeniedHandler)
                )
//                .formLogin(formLogin ->
//                        formLogin.loginPage("/login"))
                // 放开这一行会报错
//                .oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .opaqueToken(opaqueTokenConfigurer ->
                                opaqueTokenConfigurer.introspectionUri("http://localhost:8762/oauth2/introspect")
                                        .introspectionClientCredentials("service-admin", "123456")

                        )
                )
                .build();
    }


    /**
     * 完全忽略对图形验证码请求的URL权限检查
     *
     * @return
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .requestMatchers("/captchas",

                        "/swagger-ui.html", "/swagger-ui/**",
                        "/v3/api-docs/**", "/doc.html", "/webjars/**","/favicon.ico",


                         "/images/**", "/css/**", "/fonts/**",
                        "/assets/**", "/error/**"
                );
    }

}
