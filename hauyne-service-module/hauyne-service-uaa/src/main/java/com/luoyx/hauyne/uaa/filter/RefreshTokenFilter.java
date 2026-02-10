//package com.luoyx.hauyne.uaa.filter;
//
//import com.luoyx.hauyne.security.util.OAuth2CookieHelper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.web.filter.GenericFilterBean;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * RefreshToken过滤器
// * <p>
// * 由于 oauth2.0框架的刷新token接口的请求参数refresh_token是从请求体的参数中获取的，特增加该过滤器；该过滤器会优先于BasicAuthenticationFilter执行
// * <p>
// * 在执行刷新token的接口之前，提取Cookie中的refresh_token,写入到请求体的参数中
// *
// * @author luoyingxiong
// */
//@Slf4j
//public class RefreshTokenFilter extends GenericFilterBean {
//
//    /**
//     * Check access token cookie and refresh it, if it is either not present, expired or about to expire.
//     */
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
//            throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//
//        Cookie refreshCookie = OAuth2CookieHelper.getRefreshTokenCookie(httpServletRequest);
//        if (refreshCookie == null) {
//            filterChain.doFilter(httpServletRequest, servletResponse);
//        } else {
//            Map<String, String[]> map = new HashMap<>();
//            map.put(OAuth2AccessToken.REFRESH_TOKEN, new String[]{refreshCookie.getValue()});
//
//            CustomHttpServletRequest customHttpServletRequest = new CustomHttpServletRequest(httpServletRequest, map);
//            filterChain.doFilter(customHttpServletRequest, servletResponse);
//        }
//    }
//}
