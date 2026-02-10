//package com.luoyx.hauyne.uaa.config;
//
//import com.github.bingoohuang.patchca.background.MyCustomBackgroundFactory;
//import com.github.bingoohuang.patchca.custom.ConfigurableCaptchaService;
//import com.github.bingoohuang.patchca.filter.predefined.DoubleRippleFilterFactory;
//import com.github.bingoohuang.patchca.word.RandomWordFactory;
//import com.luoyx.hauyne.uaa.constant.Constant;
//import com.luoyx.hauyne.uaa.authentication.captcha.CaptchaGrantAuthenticationProvider;
//import com.luoyx.hauyne.uaa.sys.service.impl.UserDetailsServiceImpl;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.awt.*;
//import java.util.Random;
//
///**
// * Spring Security配置
// *
// * @author LuoYingxiong
// * @date 2020/5/10 20:45
// * @EnableWebSecurity 开启Web保护功能
// * @EnableGlobalMethodSecurity(prePostEnabled = true) 开启在方法上的保护功能
// */
//@Slf4j
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final UserDetailsService userDetailsService;
//    private final RedisTemplate redisTemplate;
//
//    public WebSecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl, RedisTemplate redisTemplate) {
//        this.userDetailsService = userDetailsServiceImpl;
//        this.redisTemplate = redisTemplate;
//    }
//
//    @Bean
//    public PasswordEncoder bcryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public CaptchaGrantAuthenticationProvider captchaAuthenticationProvider() {
//        return new CaptchaGrantAuthenticationProvider(userDetailsService, bcryptPasswordEncoder(), redisTemplate);
//    }
//
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(bcryptPasswordEncoder())
//                .and().authenticationProvider(captchaAuthenticationProvider());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        log.info("执行Spring Security配置");
//    }
//
//
//
//    /**
//     * 配置验证管理的 Bean
//     * 为了实现 OAuth2 的 password 模式必须要指定的授权管理 Bean。
//     *
//     * @return
//     * @throws Exception
//     */
//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers(Constant.CAPTCHAS);
//    }
//
//    /**
//     * 图形验证码配置
//     *
//     * @return
//     */
//    @Bean
//    public ConfigurableCaptchaService captchaService() {
//        ConfigurableCaptchaService captchaService = new ConfigurableCaptchaService();
//        Random random = new Random();
//        captchaService.setColorFactory((x) -> {
//            int[] c = new int[3];
//            int i = random.nextInt(c.length);
//            for (int fi = 0; fi < c.length; fi++) {
//                if (fi == i) {
//                    c[fi] = random.nextInt(71);
//                } else {
//                    c[fi] = random.nextInt(256);
//                }
//            }
//            return new Color(c[0], c[1], c[2]);
//        });
//        RandomWordFactory wf = new RandomWordFactory();
//        wf.setCharacters("23456789abcdefghigkmnpqrstuvwxyzABCDEFGHIGKLMNPQRSTUVWXYZ");
//        wf.setMaxLength(4);
//        wf.setMinLength(4);
//        captchaService.setWordFactory(wf);
//        captchaService.setBackgroundFactory(new MyCustomBackgroundFactory());
//        captchaService.setFilterFactory(new DoubleRippleFilterFactory());
//
//        return captchaService;
//    }
//}