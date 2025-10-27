package com.luoyx.hauyne.web.exception;

/**
 * 用户请求参数错误异常
 *
 * @author luoyingxiong
 */
public class ValidateException extends RuntimeException {


    public ValidateException() {
    }

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidateException(Throwable cause) {
        super(cause);
    }

    public ValidateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
