//package com.luoyx.hauyne.uaa.config.translator;
//
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import com.fasterxml.jackson.databind.ser.std.StdSerializer;
//import com.luoyx.hauyne.api.APIError;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.IOException;
//
//
///**
// * 自定义OAuth2.0异常序列化器
// *
// * @author 罗英雄
// * @date 2021/4/22 14:43
// */
//@Slf4j
//public class MyOAuth2ExceptionJackson2Serializer extends StdSerializer<MyOAuth2Exception> {
//
//    private static final long serialVersionUID = 3452342176378296244L;
//
//    public MyOAuth2ExceptionJackson2Serializer() {
//        super(MyOAuth2Exception.class);
//    }
//
//
//    @Override
//    public void serialize(MyOAuth2Exception value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//
//        APIError<?> apiError;
//        try {
//            // 使用 TypeReference 处理泛型信息
//            TypeReference<APIError<?>> typeReference = new TypeReference<APIError<?>>() {
//            };
//            String errorMsg = value.getMessage();
//            log.info("异常信息：{}", errorMsg);
//            if (errorMsg.startsWith("{") && errorMsg.endsWith("}")) {
//                apiError = objectMapper.readValue(value.getMessage(), typeReference);
//            } else {
//                apiError = APIError.internalServerError(errorMsg);
//            }
//        } catch (JsonProcessingException ex) {
//            log.error("JSON反序列化异常 {}", ex.getMessage(), ex);
//            apiError = APIError.internalServerError(ex.getMessage());
//        }
//        objectMapper.writeValue(jgen, apiError);
//        jgen.close();
//    }
//}