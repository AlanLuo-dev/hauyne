package com.luoyx.hauyne.feign.exception;

import com.luoyx.hauyne.api.APIError;
//import com.netflix.hystrix.exception.HystrixBadRequestException;
import lombok.Getter;

/**
 * 该异常继承了HystrixBadRequestException，表示客户端错误（Http 4xx系列状态码）
 * 即是说，在调用远程服务时，客户端传递的参数错误，或者客户端传递的状态错误。
 * 这种错误不会触发熔断、不会统计失败次数、也不会触发熔断。
 * 这种错误通常是由于客户端传递的参数错误导致的，
 * 比如：
 * 参数为空、参数格式错误、参数值错误等、状态已经过期、状态已经被删除、
 * 状态已经被修改等。参数为空、参数格式错误、参数值错误等。
 *
 * <p>
 * 有时我们并不希望方法进入熔断逻辑，只是把异常原样往外抛。这种情况我们只需要捉住两个点：不进入熔断、原样。
 * 原样就是获取原始的异常，上面已经介绍过了，而不进入熔断，需要把异常封装成HystrixBadRequestException，对于HystrixBadRequestException，Feign会直接抛出，不进入熔断方法。
 *
 * @author 罗英雄
 */
@Getter
public class RemoteBizFailureException /*extends HystrixBadRequestException*/ extends Exception implements RemoteFailureInformation {

    private final Integer httpStatus;

    private final String remoteMethod;

    private final String remoteHost;

    private final String remoteUrl;

    private final transient APIError<?> apiError;

    public RemoteBizFailureException(Integer httpStatus,
                                     APIError<?> apiError,
                                     String remoteMethod,
                                     String remoteHost,
                                     String remoteUrl) {
//        super(RemoteFailureInformation.buildMessage(httpStatus, apiError, remoteMethod, remoteHost, remoteUrl));
        this.httpStatus = httpStatus;
        this.apiError = apiError;
        this.remoteMethod = remoteMethod;
        this.remoteHost = remoteHost;
        this.remoteUrl = remoteUrl;
    }
}
