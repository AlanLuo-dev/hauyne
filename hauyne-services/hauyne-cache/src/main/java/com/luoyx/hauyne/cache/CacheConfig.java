package com.luoyx.hauyne.cache;

import com.luoyx.hauyne.cache.client.UserProfileClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableFeignClients
public class CacheConfig {

    private final RedisTemplate<String, Object> anotherRedisTemplate;
    private final UserProfileClient userProfileClient;

    @Bean
    public UserCache userCache() {
        log.info("缓存配置: 创建Bean -> {}", UserCache.class);
        return new UserCache(anotherRedisTemplate, userProfileClient);
    }
}
