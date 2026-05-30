package com.luoyx.hauyne.monitor.config;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
//
///**
// * 资源服务器配置
// *
// * @author 罗英雄
// */
//@Slf4j
//@RequiredArgsConstructor
//@Configuration
//@EnableResourceServer
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {
//
//    private final BearerTokenExtractor cookieTokenExtractor;
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/v2/api-docs", "/**/*.js", "/**/*.css", "/**/*.svg", "/**/*.png").permitAll()
//
//                // 开放给 spring-boot-admin的 心跳请求链接actuator/health 监控用，否则一直是Down状态
//                .antMatchers("/actuator/**").permitAll()
//
//                .anyRequest().authenticated().and().csrf().disable();
//    }
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.tokenExtractor(cookieTokenExtractor);
//    }
//
//
//}



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

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
                        "/v3/api-docs/**", "/doc.html", "/webjars/**", "/favicon.ico",
                        "/actuator/**"
                );
    }
}
