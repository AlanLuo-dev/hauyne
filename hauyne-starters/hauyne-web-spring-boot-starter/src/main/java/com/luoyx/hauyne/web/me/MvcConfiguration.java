package com.luoyx.hauyne.web.me;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Bean
    public BaseEnumConverterFactory<?> booleanToBaseEnumConverterFactory() { // 枚举转换器工厂
        return new BaseEnumConverterFactory<>();  // 枚举转换器工厂: 将Serializable类型的枚举值转换为BaseEnum枚举对象
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(booleanToBaseEnumConverterFactory());
    }

}

