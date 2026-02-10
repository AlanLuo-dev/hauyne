package com.luoyx.hauyne.feign.exception;


import com.luoyx.hauyne.api.APIError;

/**
 * 远程调用失败信息接口
 *
 * @author 罗英雄
 */
public interface RemoteFailureInformation {

    /**
     * 获取http状态码
     *
     * @return http状态码
     */
    Integer getHttpStatus();

    /**
     * 获取远程调用方法
     *
     * @return 远程调用方法
     */
    String getRemoteMethod();

    /**
     * 获取远程调用地址
     *
     * @return 远程调用地址
     */
    String getRemoteUrl();

    /**
     * 获取远程调用主机
     *
     * @return 远程调用主机
     */
    String getRemoteHost();

    <U> APIError<U> getApiError();

    static String buildMessage(Integer httpStatus,
                                APIError<?> apiError,
                                String remoteMethod,
                                String remoteHost,
                                String remoteUrl) {
        if (apiError == null) {
            return remoteMethod + " execute failure, " +
                    "httpStatus=" + httpStatus + ", " +
                    "remote APIError is null, " +
                    "remoteHost=" + remoteHost + ", " +
                    "remoteUrl=" + remoteUrl;
        } else {
            return remoteMethod + " execute failure, " +
                    "httpStatus=" + httpStatus + ", " +
                    "remoteCode=" + apiError.getCode() + ", " +
                    "remoteMessage=" + apiError.getMsg() + ", " +
                    "errorTips=" + apiError.getErrorTips() + ", " +
                    "remoteHost=" + remoteHost + ", " +
                    "remoteUrl=" + remoteUrl;
        }
    }
}
