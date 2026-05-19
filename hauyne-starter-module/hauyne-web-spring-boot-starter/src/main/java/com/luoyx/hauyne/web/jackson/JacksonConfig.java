package com.luoyx.hauyne.web.jackson;

import com.luoyx.hauyne.web.enumsupport.jackson.EnumSpecDeserializerModifier;
import com.luoyx.hauyne.web.enumsupport.jackson.EnumSpecSerializerModifier;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;

import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.cfg.EnumFeature;
import tools.jackson.databind.module.SimpleModule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     * LocalDateTime 的序列化
     */
    private static class LocalDateTimeSerializer extends ValueSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
            if (value != null) {
                gen.writeString(value.format(DATE_TIME_FORMATTER));
            } else {
                gen.writeNull();
            }
        }
    }

    /**
     * LocalDateTime 的反序列化
     */
    private static class LocalDateTimeDeserializer extends ValueDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
            String dateString = p.getString();
            if (dateString == null || dateString.trim().isEmpty()) {
                return null;
            }
            return LocalDateTime.parse(dateString, DATE_TIME_FORMATTER);
        }
    }

    /**
     * 日期的序列化
     */
    private static class LocalDateSerializer extends ValueSerializer<LocalDate> {
        @Override
        public void serialize(LocalDate value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException{
            if (value != null) {
                gen.writeString(value.format(DATE_FORMATTER));
            } else {
                gen.writeNull();
            }
        }
    }

    /**
     * 日期的反序列化
     */
    private static class LocalDateDeserializer extends ValueDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext ctxt)  throws JacksonException{
            String dateString = p.getString();
            if (dateString == null || dateString.trim().isEmpty()) {
                return null;
            }
            return LocalDate.parse(dateString, DATE_FORMATTER);
        }
    }

    @Bean
    public JsonMapperBuilderCustomizer jacksonCustomizer(SimpleModule enumSchemaModule) {

        //JavaTimeModule javaTimeModule = new JavaTimeModule();
        //javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        //javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        //javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        //javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        return builder -> {
            builder.addModules(
                enumSchemaModule,
                new SimpleModule()
                    .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer())
                    .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer())
                    
                    .addSerializer(LocalDate.class, new LocalDateSerializer())
                    .addDeserializer(LocalDate.class, new LocalDateDeserializer())
                )
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .enable(
                    DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS,
                    DeserializationFeature.FAIL_ON_TRAILING_TOKENS
                )
                .disable(
                    EnumFeature.WRITE_ENUMS_USING_TO_STRING
                );
        };
    }


    @Bean
    public SimpleModule enumSchemaModule() {
        SimpleModule module = new SimpleModule();
        module.setSerializerModifier(new EnumSpecSerializerModifier());
        module.setDeserializerModifier(new EnumSpecDeserializerModifier());
        return module;
    }
}
