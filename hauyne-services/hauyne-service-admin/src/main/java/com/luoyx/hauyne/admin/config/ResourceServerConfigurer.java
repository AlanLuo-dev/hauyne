package com.luoyx.hauyne.admin.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.luoyx.hauyne.admin.api.sys.UserAPI.LOGIN_LOOKUP;

/**
 * @author 罗英雄
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
public class ResourceServerConfigurer {

    /**
     * 此处配置，Spring Security将完全忽略对请求的URL权限检查，是最宽松的配置
     *
     * @return
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .requestMatchers(
                        "/swagger-ui.html", "/swagger-ui/**",
                        "/v3/api-docs/**", "/doc.html", "/webjars/**","/favicon.ico",


                        "/sys/users/find-by-username",
                        "/sys/authorities/findAuthoritiesByUserId",
                        "/sys/authorities/buildMenuTree",
                        "/userInfo/**",
                        LOGIN_LOOKUP,
                        "/sys/roles/testEnum",
                        "/sys/roles/testBoolEnum",
                        "/sys/roles/testJsonEnum"
                )
                .requestMatchers(HttpMethod.POST, "/sys/login-histories");
    }

    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

//@Slf4j
//@RequiredArgsConstructor
//@Configuration
//@EnableResourceServer
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {
//
//    private final BearerTokenExtractor cookieTokenExtractor;
//    private final AuthenticationEntryPoint authEntryPoint;
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        log.info("执行资源服务器配置");
//        http.authorizeRequests()
//
//                // 开放给 spring-boot-admin的 心跳请求链接actuator/health 监控用，否则一直是Down状态
//                .antMatchers(
//                        "/v2/api-docs/**",
//                        "/actuator/**",
//                ).permitAll()
//                .anyRequest().authenticated()
//                .and().csrf().disable()
//                .exceptionHandling().authenticationEntryPoint(authEntryPoint)
//        ;
//    }
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        log.info("---------------- configure");
//
//        resources.tokenExtractor(cookieTokenExtractor);
//    }
//
//    @Bean
//    public PasswordEncoder bcryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
