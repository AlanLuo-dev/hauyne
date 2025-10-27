package com.luoyx.hauyne.security.autoconfigure;


import com.luoyx.hauyne.security.filter.UserContextFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * @author luoyingxiong
 */
@RequiredArgsConstructor
public class UserContextFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final UserContextFilter userContextFilter;

    @Override
    public void configure(HttpSecurity http) {

        // 将 UserContextFilter 添加到 SecurityFilterChain
        http.addFilterAfter(userContextFilter, AbstractPreAuthenticatedProcessingFilter.class);
    }
}
