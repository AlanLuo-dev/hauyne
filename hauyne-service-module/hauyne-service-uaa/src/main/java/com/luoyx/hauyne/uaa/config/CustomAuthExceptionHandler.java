package com.luoyx.hauyne.uaa.config;

import com.luoyx.hauyne.api.APIError;
import com.luoyx.hauyne.api.enums.ErrorCodeEnum;
import com.luoyx.hauyne.framework.utils.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthExceptionHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(@NotNull HttpServletRequest request,
                                        HttpServletResponse response,
                                        @NotNull AuthenticationException exception)
            throws IOException, ServletException {

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        String errorTips;
        if (exception instanceof OAuth2AuthenticationException oauth2Exception) {
            String errorCode = oauth2Exception.getError().getErrorCode();
            String grantType = request.getParameter("grant_type");

            if (OAuth2ErrorCodes.INVALID_GRANT.equals(errorCode)
                    && AuthorizationGrantType.REFRESH_TOKEN.getValue().equals(grantType)) {
                errorTips = "登录超时";
            } else {
                errorTips = errorCode;
            }
        } else {
            errorTips = exception.getMessage();
        }
        APIError<?> apiError = new APIError<>(ErrorCodeEnum.USER_LOGIN_HAS_EXPIRED, errorTips);

        response.getWriter().write(JsonUtil.toString(apiError));
    }
}

