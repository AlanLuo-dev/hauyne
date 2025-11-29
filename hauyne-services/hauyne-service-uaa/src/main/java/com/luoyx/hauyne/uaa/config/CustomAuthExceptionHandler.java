package com.luoyx.hauyne.uaa.config;

import com.luoyx.hauyne.api.APIError;
import com.luoyx.hauyne.api.enums.ErrorCodeEnum;
import com.luoyx.hauyne.framework.utils.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthExceptionHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        APIError<?> apiError = new APIError<>(
                ErrorCodeEnum.USER_REQUEST_PARAMETER_ERROR,
                exception.getMessage()
        );

        response.getWriter().write(JsonUtil.toString(apiError));
    }
}

