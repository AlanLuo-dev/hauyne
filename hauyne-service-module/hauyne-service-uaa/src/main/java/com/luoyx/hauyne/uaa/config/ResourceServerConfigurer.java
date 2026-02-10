//package com.luoyx.hauyne.uaa.config;
//
//import com.luoyx.hauyne.uaa.authentication.logout.CustomLogoutHandler;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
//import org.springframework.security.web.authentication.logout.LogoutHandler;
//
///**
// * @author LuoYingxiong
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
//        log.info("执行 ==> 资源服务器配置");
//        http
//                .authorizeRequests()
//                .antMatchers("/v2/api-docs/**").permitAll()
//
//                // 开放给 spring-boot-admin的 心跳请求链接actuator/health 监控用，否则一直是Down状态
//                .antMatchers("/actuator/**").permitAll()
//
//                // hystrix熔断器
//                .antMatchers("/hystrix.stream", "/hystrix/**", "/webjars/**", "/favicon.ico").permitAll()
//                .anyRequest().authenticated()
//                .and().csrf().disable()
//                .cors()
//                .and()
//                .logout()
//                .addLogoutHandler(customLogoutHandler());
//    }
//
//
//    @Bean
//    public LogoutHandler customLogoutHandler() {
//        return new CustomLogoutHandler();
//    }
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        log.info("---------------- configure");
//
//        resources.tokenExtractor(cookieTokenExtractor);
//    }
//}
//
