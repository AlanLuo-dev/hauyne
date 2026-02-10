//package com.luoyx.hauyne.message.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//
///**
// * @author 罗英雄
// * @date 2021/9/9 15:49
// */
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
//
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception{
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/refresh**",
//                        "/actuator/**",
//                        "/doc**", "/v2/api-docs",
//                        "/turbine/**",
//                        "/websocket/**",
//                        "/verifyCode",
//                        "/needCodeForSend",
//                        "/sendVerifySms")
//                .permitAll()
//        .anyRequest().authenticated();
//    }
//}
