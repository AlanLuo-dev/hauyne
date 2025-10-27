//package com.luoyx.hauyne.security.autoconfigure;
//
//import com.luoyx.hauyne.security.context.UserContextHolder;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//public class UserContextInterceptor implements HandlerInterceptor {
//
//    @Override
//    public void afterCompletion(HttpServletRequest request,
//                                HttpServletResponse response,
//                                Object handler,
//                                Exception ex) throws Exception {
//        UserContextHolder.clear();
//    }
//}
//
