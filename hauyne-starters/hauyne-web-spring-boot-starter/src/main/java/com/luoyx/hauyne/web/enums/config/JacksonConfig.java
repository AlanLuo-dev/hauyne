package com.luoyx.hauyne.web.enums.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.luoyx.hauyne.web.enums.jackson.EnumDefDeserializerModifier;
import com.luoyx.hauyne.web.enums.jackson.EnumDefSerializerModifier;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer(SimpleModule enumSchemaModule) {

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        return builder -> builder
                // EnumDef 支持
                .modules(enumSchemaModule, javaTimeModule)

                // 枚举 & BigDecimal
                .featuresToDisable(
                        SerializationFeature.WRITE_ENUMS_USING_TO_STRING,       // 序列化时，禁用将枚举值转换为字符串
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
                )
                .featuresToEnable(
                        DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS,      // 反序列化时，将float、double类型的字符串转换为BigDecimal
                        DeserializationFeature.FAIL_ON_TRAILING_TOKENS          // 开启 jackson 反序列化的 严格模式
                );
    }


    @Bean
    public SimpleModule enumSchemaModule() {
        SimpleModule module = new SimpleModule();
        module.setSerializerModifier(new EnumDefSerializerModifier());
        module.setDeserializerModifier(new EnumDefDeserializerModifier());
        return module;
    }
}
