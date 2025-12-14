package com.luoyx.hauyne.web.enums.config;

import com.luoyx.hauyne.web.enums.convert.EnumSchemaConverterFactory;
import com.luoyx.hauyne.web.enums.core.EnumSchema;
import com.luoyx.hauyne.web.enums.springdoc.CodeEnumModelConverter;
import com.luoyx.hauyne.web.enums.springdoc.CodeEnumPropertyCustomizer;
import com.luoyx.hauyne.web.enums.springdoc.EnumParameterCustomizer;
import io.swagger.v3.core.jackson.ModelResolver;
import org.springdoc.core.customizers.ParameterCustomizer;
import org.springdoc.core.customizers.PropertyCustomizer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class EnumWebMvcConfiguration implements WebMvcConfigurer {

    @Bean
    public EnumSchemaConverterFactory<?> booleanToBaseEnumConverterFactory() { // 枚举转换器工厂
        return new EnumSchemaConverterFactory<>();  // 枚举转换器工厂: 将Serializable类型的枚举值转换为BaseEnum枚举对象
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(booleanToBaseEnumConverterFactory());
    }


    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(EnumSchema.class)
    public static class CodeEnumPropertyCustomizerConfiguration implements InitializingBean {

        @Bean
        public PropertyCustomizer codeEnumPropertyCustomizer() {
            return new CodeEnumPropertyCustomizer();
        }

        @Bean
        public ParameterCustomizer enumParameterCustomizer() {
            return new EnumParameterCustomizer();
        }

        @Bean
        public ModelResolver codeEnumModelResolver() {
            return new CodeEnumModelConverter();
        }

        @Override
        public void afterPropertiesSet() throws Exception {
//            Json.mapper();
        }
    }
}

