package com.luoyx.hauyne.jpa.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA 配置类
 *
 * @author Alan.Luo
 * @since 2023/4/27 23:43
 */
@Slf4j
@Configuration
@EnableJpaAuditing
public class JpaConfiguration {

    /**
     * Spring Data JPA 审计 Bean
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "myAuditorAware")
    public AuditorAware<Long> myAuditorAware() {
        log.info("JPA配置: 创建JPA自动审计Bean -> {}", MyAuditorAware.class);
        return new MyAuditorAware();
    }
}
