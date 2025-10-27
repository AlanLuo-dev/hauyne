package com.luoyx.hauyne.uaa.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Feign Client配置类
 *
 * @author luoyingxiong
 */
@Configuration
public class FeignConfig {

    /**
     * OpenFeign 的请求日志级别，建议FULL
     *
     * @return
     */
    @Bean
    Logger.Level feignLevel() {
        return Logger.Level.BASIC;
    }

    @Bean
    public Retryer feignRetryer() {

        /**
         * 配置请求失败时代重试的策略
         *
         * 重试间隔为100毫秒，最大重试时间为1秒，重试次数为5次
         */
        return new Retryer.Default(100, TimeUnit.SECONDS.toMillis(1), 5);
    }
}
