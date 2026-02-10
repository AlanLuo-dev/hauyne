package com.luoyx.hauyne.feign;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luoyx.hauyne.api.APIError;
import com.luoyx.hauyne.api.enums.ErrorCodeEnum;
import com.luoyx.hauyne.feign.exception.RemoteBizFailureException;
import com.luoyx.hauyne.feign.exception.RemoteServerErrorException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

/**
 * 类<code>PlatformFeignErrorDecoder</code>
 * 用于：TODO
 *
 * @author zt19191
 * @version 1.0
 * @date 2021/6/10 14:51
 */
@Slf4j
public class PlatformFeignErrorDecoder implements ErrorDecoder {

    // 声明Feign默认的错误解码器对象（Default本就是Feign框架的默认实现类）
    private final ErrorDecoder errorDecoder = new Default();


    @Override
    public Exception decode(String methodKey, Response response) {
        final String remoteUrl = response.request().url();
        String remoteHost = null;
        try {
            remoteHost = new URI(remoteUrl).getHost();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // 判断Http响应状态码是【客户端错误】还是【服务器端错误】
        final int statusCode = response.status();
        final HttpStatus httpStatus = HttpStatus.valueOf(statusCode);

        // 客户端错误
        if (httpStatus.is4xxClientError()) {
            APIError<?> apiError = this.extract(response);

            // 404场景：区分是【资源不存在】还是【URL不存在】
            if (HttpStatus.NOT_FOUND.value() == statusCode) {
                final String code = apiError.getCode();

                /**
                 * Feign调用提示URL不存在，是服务器端开发人员写错URL而导致，与请求发起方的终端（浏览器端）无关，所以，从浏览器端来看，应该视为服务器端错误
                 * 这里抛服务器端异常，返回500状态码
                 */
                if (ErrorCodeEnum.URL_NOT_FOUND.getCode().equals(code)) {
                    return new RemoteServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR.value(), apiError, methodKey, remoteHost, remoteUrl);
                }
            }

            return new RemoteBizFailureException(statusCode, apiError, methodKey, remoteHost, remoteUrl);
        }

        // 服务器端错误
        if (httpStatus.is5xxServerError()) {
            APIError<?> apiError = this.extract(response);
            return new RemoteServerErrorException(statusCode, apiError, methodKey, remoteHost, remoteUrl);
        }

        // 其他情况使用Feign默认的错误解码器对象进行处理
        return errorDecoder.decode(methodKey, response);
    }

    /**
     * 从响应体中提取APIError对象
     *
     * @param response Feign的响应体对象
     * @return APIError对象
     */
    private APIError<?> extract(Response response) {
        Response.Body body = response.body();
        if (body == null) {
            return null;
        }
        try {
            String responseStr = Util.toString(body.asReader(StandardCharsets.UTF_8));
            ObjectMapper mapper = new ObjectMapper();

            // 忽略未知属性（如果JSON中包含Java类中没有定义的字段，Jackson 会自动忽略这些字段，不抛异常）
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            APIError<?> apiError = mapper.readValue(responseStr, APIError.class);
            if (!StringUtils.isAnyEmpty(apiError.getCode(), apiError.getMsg())) {
                return apiError;
            }
        } catch (IOException e) {
            log.error("io error", e);
        }

        return null;
    }
}
