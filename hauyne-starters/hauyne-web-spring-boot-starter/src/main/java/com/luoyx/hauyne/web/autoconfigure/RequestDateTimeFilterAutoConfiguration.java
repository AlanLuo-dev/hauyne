package com.luoyx.hauyne.web.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@ConditionalOnMissingBean(RequestDateTimeFilter.class)
public class RequestDateTimeFilterAutoConfiguration {

    @Bean
    public FilterRegistrationBean<RequestDateTimeFilter> requestDateTimeFilter() {
        FilterRegistrationBean<RequestDateTimeFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestDateTimeFilter());
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE); // 设置为最前置
        registrationBean.addUrlPatterns("/*"); // 或根据实际匹配路径
        return registrationBean;
    }
}

