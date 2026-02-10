package com.luoyx.hauyne.openfeign.autoconfigure;

import com.luoyx.hauyne.feign.PlatformFeignErrorDecoder;
import com.luoyx.hauyne.openfeign.interceptor.FeignCallTraceInterceptor;
import feign.Feign;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;

/**
 * 类<code>FeignAutoConfiguration</code>
 * 用于：TODO
 *
 * @author zt19191
 * @version 1.0
 * @date 2021/6/10 14:57
 */
@SpringBootConfiguration
@ConditionalOnClass({Feign.class, FeignClient.class})
public class FeignAutoConfiguration {

    @Bean
    public ErrorDecoder getErrorDecoder() {
        return new PlatformFeignErrorDecoder();
    }

    @Bean
    public RequestInterceptor feignCallTraceInterceptor() {
        return new FeignCallTraceInterceptor();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

}
