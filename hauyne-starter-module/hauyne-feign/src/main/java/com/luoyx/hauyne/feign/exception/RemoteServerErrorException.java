package com.luoyx.hauyne.feign.exception;


import com.luoyx.hauyne.api.APIError;
import lombok.Getter;
import lombok.ToString;

/**
 * 类<code>RemoteServerErrorException</code>
 * 用于：远端服务器异常，会被hystrix熔断计数器记录
 *
 * @author zt19191
 * @version 1.0
 * @date 2021/6/10 14:50
 */
@Getter
@ToString
public class RemoteServerErrorException extends RuntimeException implements RemoteFailureInformation {

    private final Integer httpStatus;

    private final String remoteMethod;

    private final String remoteHost;

    private final String remoteUrl;

    private final transient APIError<?> apiError;

    public RemoteServerErrorException(Integer httpStatus,
                                      APIError<?> apiError,
                                      String remoteMethod,
                                      String remoteHost,
                                      String remoteUrl) {
        super(RemoteFailureInformation.buildMessage(httpStatus, apiError, remoteMethod, remoteHost, remoteUrl));
        this.httpStatus = httpStatus;
        this.apiError = apiError;
        this.remoteMethod = remoteMethod;
        this.remoteHost = remoteHost;
        this.remoteUrl = remoteUrl;
    }

}
