package com.luoyx.hauyne.web.enumsupport.config;


import com.luoyx.hauyne.web.enumsupport.convert.EnumSpecConverterFactory;
import com.luoyx.hauyne.api.enumdef.EnumSpec;
import com.luoyx.hauyne.web.enumsupport.springdoc.EnumSpecModelConverter;
import com.luoyx.hauyne.web.enumsupport.springdoc.EnumSpecParameterCustomizer;
import com.luoyx.hauyne.web.enumsupport.springdoc.EnumSpecPropertyCustomizer;
import com.luoyx.hauyne.web.enumsupport.validate.EnumSpecStartupValidator;
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
        return new EnumSpecStartupValidator(applicationContext);
    }

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

