package com.luoyx.hauyne.web.enums.config;


import com.luoyx.hauyne.web.enums.convert.EnumDefConverterFactory;
import com.luoyx.hauyne.api.enums.core.EnumDefinition;
import com.luoyx.hauyne.web.enums.springdoc.EnumDefModelConverter;
import com.luoyx.hauyne.web.enums.springdoc.EnumDefParameterCustomizer;
import com.luoyx.hauyne.web.enums.springdoc.EnumDefPropertyCustomizer;
import com.luoyx.hauyne.web.enums.validate.EnumDefStartupValidator;
import io.swagger.v3.core.jackson.ModelResolver;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.ParameterCustomizer;
import org.springdoc.core.customizers.PropertyCustomizer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class EnumWebMvcConfiguration implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;

    @Bean
    public SmartInitializingSingleton enumDefStartupValidator(){
        return new EnumDefStartupValidator(applicationContext);
    }

    @Bean
    public EnumDefConverterFactory<?> booleanToBaseEnumConverterFactory() { // 枚举转换器工厂
        return new EnumDefConverterFactory<>();  // 枚举转换器工厂: 将Serializable类型的枚举值转换为BaseEnum枚举对象
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(booleanToBaseEnumConverterFactory());
    }


    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(EnumDefinition.class)
    public static class CodeEnumPropertyCustomizerConfiguration implements InitializingBean {

        @Bean
        public PropertyCustomizer codeEnumPropertyCustomizer() {
            return new EnumDefPropertyCustomizer();
        }

        @Bean
        public ParameterCustomizer enumParameterCustomizer() {
            return new EnumDefParameterCustomizer();
        }

        @Bean
        public ModelResolver codeEnumModelResolver() {
            return new EnumDefModelConverter();
        }

        @Override
        public void afterPropertiesSet() throws Exception {
//            Json.mapper();
        }
    }
}

