package com.luoyx.hauyne.security.autoconfigure;

import com.luoyx.hauyne.api.APIError;
import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Rommel
 * @version 1.0
 * @date 2023/7/31-10:44
 * @description TODO
 */
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        if (authException instanceof InsufficientAuthenticationException) {
            String accept = request.getHeader("accept");
            if (accept.contains(MediaType.TEXT_HTML_VALUE)) {
                //如果是html请求类型，则返回登录页
                LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint = new LoginUrlAuthenticationEntryPoint("/login");
                loginUrlAuthenticationEntryPoint.commence(request, response, authException);
            } else {
                // 未认证
                unAuthResponse(response);
            }
        } else if (authException instanceof InvalidBearerTokenException) {
            exceptionResponse(response, "令牌无效或已过期");
        } else {
            exceptionResponse(response, authException);
        }

    }

    /**
     *
     * @param response
     * @param e
     * @throws IOException
     * @author Rommel
     * @date 2023/7/31-10:45
     * @version 1.0
     * @description 异常响应
     */
    public static void exceptionResponse(HttpServletResponse response, Exception e) throws AccessDeniedException, AuthenticationException, IOException {

        String message = null;
        if (e instanceof OAuth2AuthenticationException o) {
            message = o.getError().getDescription();
        } else {
            message = e.getMessage();
        }
        exceptionResponse(response, message);
    }

    public static void exceptionResponse(HttpServletResponse response, String message) throws AccessDeniedException, AuthenticationException, IOException {

        APIError<?> responseResult = APIError.invalidParam(message);
        Gson gson = new Gson();
        String jsonResult = gson.toJson(responseResult);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().print(jsonResult);

    }

    public static void unAuthResponse(HttpServletResponse response) throws AccessDeniedException, AuthenticationException, IOException {

        APIError<?> responseResult = APIError.unAuth("未登录");
        Gson gson = new Gson();
        String jsonResult = gson.toJson(responseResult);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().print(jsonResult);

    }
}