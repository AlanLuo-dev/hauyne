package com.luoyx.hauyne.web.yiyiyiyi;

import io.swagger.v3.core.jackson.ModelResolver;
import org.springdoc.core.customizers.PropertyCustomizer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CodeEnumWebMvcConfigurer implements WebMvcConfigurer {

    @Bean
    public CodeEnumConverterFactory codeEnumConverterFactory() {
        return new CodeEnumConverterFactory<>();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // org.springframework.core.convert.support.GenericConversionService.ConvertersForPair.add
        // this.converters.addFirst(converter);
        // 所以我们自定义的会放在前面
        registry.addConverterFactory(codeEnumConverterFactory());
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(CodeEnum.class)
    public static class CodeEnumPropertyCustomizerConfiguration implements InitializingBean {

        @Bean
        public PropertyCustomizer codeEnumPropertyCustomizer() {
            return new CodeEnumPropertyCustomizer();
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
