package com.luoyx.hauyne.web.enumsupport.config;


import com.luoyx.hauyne.api.enumsupport.EnumSpec;
import com.luoyx.hauyne.web.enumsupport.convert.EnumSpecConverterFactory;
import com.luoyx.hauyne.web.enumsupport.springdoc.EnumSpecModelConverter;
import com.luoyx.hauyne.web.enumsupport.springdoc.EnumSpecParameterCustomizer;
import com.luoyx.hauyne.web.enumsupport.springdoc.EnumSpecPropertyCustomizer;
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
    public EnumSpecConverterFactory<?> booleanToBaseEnumConverterFactory() { // 枚举转换器工厂
        return new EnumSpecConverterFactory<>();  // 枚举转换器工厂: 将Serializable类型的枚举值转换为BaseEnum枚举对象
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(booleanToBaseEnumConverterFactory());
    }


    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(EnumSpec.class)
    public static class CodeEnumPropertyCustomizerConfiguration implements InitializingBean {

        @Bean
        public PropertyCustomizer codeEnumPropertyCustomizer() {
            return new EnumSpecPropertyCustomizer();
        }

        @Bean
        public ParameterCustomizer enumParameterCustomizer() {
            return new EnumSpecParameterCustomizer();
        }

        @Bean
        public ModelResolver codeEnumModelResolver() {
            return new EnumSpecModelConverter();
        }

        @Override
        public void afterPropertiesSet() throws Exception {
//            Json.mapper();
        }
    }
}

