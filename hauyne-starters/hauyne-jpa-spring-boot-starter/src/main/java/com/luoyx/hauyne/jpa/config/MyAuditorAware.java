package com.luoyx.hauyne.jpa.config;

import com.luoyx.hauyne.security.context.UserContextHolder;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * JPA审计配置
 *
 * @author Alan.Luo
 * @since 2023/4/27 23:45
 */
public class MyAuditorAware implements AuditorAware<Long> {

    /**
     * 返回当前的用户id作为审计者
     *
     * @return
     */
    @Override
    public Optional<Long> getCurrentAuditor() {
        Long sysUserId = UserContextHolder.getUserId();

        return Optional.ofNullable(sysUserId);
    }
}
