//package com.luoyx.hauyne.uaa.config.translator;
//
//import com.luoyx.hauyne.api.APIError;
//import com.luoyx.hauyne.api.enums.ErrorCodeEnum;
//import com.luoyx.hauyne.feign.exception.RemoteBizFailureException;
//import com.luoyx.hauyne.feign.exception.RemoteServerErrorException;
//import com.luoyx.hauyne.framework.utils.JsonUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;
//import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
//import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
//import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
//import org.springframework.security.web.util.ThrowableAnalyzer;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//
//import java.io.IOException;
//
//@Slf4j
//public class AuthWebResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {
//
//    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();
//
//    @Override
//    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
//
//        // Try to extract a SpringSecurityException from the stacktrace
//        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);
//        Exception ase = (OAuth2Exception) throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
//
//        if (ase != null) {
//            return handleOAuth2Exception((OAuth2Exception) ase);
//        }
//
//        // 401错误
//        ase = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class,
//                causeChain);
//        if (ase != null) {
//            return handleOAuth2Exception(new UnauthorizedException(e.getMessage(), e));
//        }
//
//        // 403错误
//        ase = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
//        if (ase instanceof AccessDeniedException) {
//            return handleOAuth2Exception(new ForbiddenException(ase.getMessage(), ase));
//        }
//
//        // 不支持的Http方法
//        ase = (HttpRequestMethodNotSupportedException) throwableAnalyzer.getFirstThrowableOfType(
//                HttpRequestMethodNotSupportedException.class, causeChain);
//        if (ase instanceof HttpRequestMethodNotSupportedException) {
//            return handleOAuth2Exception(new MethodNotAllowed(ase.getMessage(), ase));
//        }
//
//        // feign调用出现的服务器端错误
//        ase = (RemoteServerErrorException) throwableAnalyzer.getFirstThrowableOfType(RemoteServerErrorException.class, causeChain);
//        if (ase != null) {
//            APIError<?> apiError1 = ((RemoteServerErrorException) ase).getApiError();
//            log.error(apiError1.getErrorTips());
//            APIError<?> apiError = new APIError<>(ErrorCodeEnum.RPC_SERVICE_ERROR);
//
//            return handleOAuth2Exception(new MyOAuth2Exception(HttpStatus.INTERNAL_SERVER_ERROR.value(), JsonUtil.toString(apiError), e));
//        }
//
//        ase = (RemoteBizFailureException) throwableAnalyzer.getFirstThrowableOfType(RemoteBizFailureException.class, causeChain);
//        if (ase != null) {
//            APIError<?> apiError1 = ((RemoteBizFailureException) ase).getApiError();
//            log.error(apiError1.getErrorTips());
//            APIError<?> apiError = new APIError<>(ErrorCodeEnum.RPC_SERVICE_ERROR, apiError1.getErrorTips());
//
//            return handleOAuth2Exception(new MyOAuth2Exception(HttpStatus.INTERNAL_SERVER_ERROR.value(), JsonUtil.toString(apiError), e));
//        }
//
//        return handleOAuth2Exception(new AuthWebResponseExceptionTranslator.ServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e));
//    }
//
//    private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) throws IOException {
//        final int status = e.getHttpErrorCode();
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Cache-Control", "no-store");
//        headers.set("Pragma", "no-cache");
//        if (status == HttpStatus.UNAUTHORIZED.value() || (e instanceof InsufficientScopeException)) {
//            headers.set("WWW-Authenticate", String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, e.getSummary()));
//        }
//
//        MyOAuth2Exception myOAuth2Exception;
//        if (e instanceof ClientAuthenticationException) {
//            APIError<?> apiError = new APIError<>(ErrorCodeEnum.USER_REQUEST_PARAMETER_ERROR, e.getMessage());
//            myOAuth2Exception = new MyOAuth2Exception(status, JsonUtil.toString(apiError), e);
//        } else {
//            myOAuth2Exception = new MyOAuth2Exception(status, e.getMessage(), e);
//        }
//
//
//        //将自定义的异常信息放入返回体中
//        return new ResponseEntity<>(myOAuth2Exception, headers, HttpStatus.valueOf(status));
//
//
//
//    }
//
//    public void setThrowableAnalyzer(ThrowableAnalyzer throwableAnalyzer) {
//        this.throwableAnalyzer = throwableAnalyzer;
//    }
//
//    /* ~ ======================= 以下是自定义OAuth2.0异常 ===================================================== */
//
//    @SuppressWarnings("serial")
//    private static class ForbiddenException extends OAuth2Exception {
//
//        public ForbiddenException(String msg, Throwable t) {
//            super(msg, t);
//        }
//
//        @Override
//        public String getOAuth2ErrorCode() {
//            return "access_denied";
//        }
//
//        @Override
//        public int getHttpErrorCode() {
//            return 403;
//        }
//
//    }
//
//    @SuppressWarnings("serial")
//    private static class ServerErrorException extends OAuth2Exception {
//
//        public ServerErrorException(String msg, Throwable t) {
//            super(msg, t);
//        }
//
//        @Override
//        public String getOAuth2ErrorCode() {
//            return "server_error";
//        }
//
//        @Override
//        public int getHttpErrorCode() {
//            return 500;
//        }
//
//    }
//
//    @SuppressWarnings("serial")
//    private static class UnauthorizedException extends OAuth2Exception {
//
//        public UnauthorizedException(String msg, Throwable t) {
//            super(msg, t);
//        }
//
//        @Override
//        public String getOAuth2ErrorCode() {
//            return "unauthorized";
//        }
//
//        @Override
//        public int getHttpErrorCode() {
//            return 401;
//        }
//
//    }
//
//    /**
//     * 不支持的Http方法 扩展异常
//     */
//    @SuppressWarnings("serial")
//    private static class MethodNotAllowed extends OAuth2Exception {
//
//        public MethodNotAllowed(String msg, Throwable t) {
//            super(msg, t);
//        }
//
//        @Override
//        public String getOAuth2ErrorCode() {
//            return "method_not_allowed";
//        }
//
//        @Override
//        public int getHttpErrorCode() {
//            return 405;
//        }
//
//    }
//}
