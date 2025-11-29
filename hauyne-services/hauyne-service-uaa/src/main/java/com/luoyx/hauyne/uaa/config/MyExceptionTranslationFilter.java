package com.luoyx.hauyne.uaa.config;

import com.luoyx.hauyne.uaa.config.MyAuthenticationEntryPoint;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author Rommel
 * @version 1.0
 * @date 2023/7/31-23:59
 * @description 异常过滤器
 */
public class MyExceptionTranslationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        }catch (Exception e) {
            if (e instanceof AuthenticationException|| e instanceof AccessDeniedException) {
                throw e;
            }
            //非AuthenticationException、AccessDeniedException异常，则直接响应
            MyAuthenticationEntryPoint.exceptionResponse(response,e);
        }

    }
}
