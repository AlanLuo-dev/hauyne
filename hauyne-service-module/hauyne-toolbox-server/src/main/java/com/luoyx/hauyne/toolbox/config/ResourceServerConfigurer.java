//package com.luoyx.hauyne.toolbox.config;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
//
///**
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
//        log.info("执行资源服务器配置");
//        http.authorizeRequests()
//                .antMatchers("/v2/api-docs/**").permitAll()
//
//                // 开放给 spring-boot-admin的 心跳请求链接actuator/health 监控用，否则一直是Down状态
//                .antMatchers("/actuator/**").permitAll()
//
//                .anyRequest().authenticated().and().csrf().disable();
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
