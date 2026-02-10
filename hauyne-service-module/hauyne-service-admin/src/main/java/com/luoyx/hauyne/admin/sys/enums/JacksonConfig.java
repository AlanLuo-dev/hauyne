//package com.luoyx.hauyne.admin.sys.enums;
//
//import com.luoyx.hauyne.web.autoconfigure.BaseEnum;
//import com.luoyx.hauyne.web.autoconfigure.EnumToObjectSerializer;
//import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class JacksonConfig {
//
//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
//        return builder -> {
//            // 对所有实现 BaseEnum 接口的枚举，使用自定义序列化器
//            builder.serializerByType(BaseEnum.class, new EnumToObjectSerializer());
//        };
//    }
//}
