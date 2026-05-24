package com.luoyx.hauyne.framework.utils;

//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.*;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.cfg.DateTimeFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * JSON工具类
 *
 * @author 1564469545@qq.com
 */
@Slf4j
@UtilityClass
public class JsonUtil {


    private static final JsonMapper OBJECT_MAPPER;

    /**
     * 时间格式
     */
    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {
        // LocalDateTime序列化处理
        SimpleModule timeModule = new SimpleModule();

        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());

        OBJECT_MAPPER = JsonMapper.builder()

                // 对象的所有字段全部列入
                .changeDefaultPropertyInclusion(incl -> incl.withValueInclusion(JsonInclude.Include.ALWAYS))
                .changeDefaultPropertyInclusion(incl -> incl.withContentInclusion(JsonInclude.Include.ALWAYS))

                // 所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
                .defaultDateFormat(new SimpleDateFormat(STANDARD_FORMAT))

                // 忽略空Bean转json的错误
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)

                // 关闭“将日期写为时间戳”，让其输出 ISO-8601 字符串
                .disable(DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS)

                // 忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

                .enable(DateTimeFeature.WRITE_DATES_WITH_ZONE_ID)
                .addModule(timeModule)
                .build();
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    /**
     * 对象转Json格式字符串
     *
     * @param obj 待转换对象
     * @param <T>
     * @return 返回转换后的json字符串
     */
    public static <T> String toString(T obj) {
        if (null == obj) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JacksonException e) {
            return null;
        }
    }

    /**
     * 支持把时间日志转成String, 格式为 yyyy-MM-dd HH:mm:ss
     *
     * @param obj 日期类型，支持Date类型和LocalDateTime
     * @param <T>
     * @return json 格式化后的日期字符串
     */
    public static <T> String toDateString(T obj) {
        if (null == obj) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JacksonException e) {
            return null;
        }
    }

    /**
     * 对象转美化的Json格式字符串
     *
     * @param obj 待转换对象
     * @param <T>
     * @return 返回美化后的Json格式字符串
     */
    public static <T> String toStringPretty(T obj) {
        if (null == obj) {
            return null;
        }
        try {
            return obj instanceof String
                    ? (String) obj
                    : OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JacksonException e) {
            return null;
        }
    }

    /**
     * 字符串转换为自定义对象
     *
     * @param str   待转换的字符串
     * @param clazz 自定义对象
     * @param <T>
     * @return 返回自定义对象
     */
    public static <T> T toObj(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str) || null == clazz) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? clazz.cast(str) : OBJECT_MAPPER.readValue(str, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 字符串转换为List
     *
     * @param str   待转换的字符串
     * @param clazz 自定义对象
     * @param <T>
     * @return 返回List对象
     */
    public static <T> List<T> toList(String str, Class<T> clazz) {
        return toCollection(str, List.class, clazz);
    }

    /**
     * 字符串转换为Map
     *
     * @param str   待转换的字符串
     * @param clazz 自定义对象
     * @param <T>
     * @return 返回Map对象
     */
    public static <T> Map<String, T> toMap(String str, Class<T> clazz) {
        return toCollection(str, Map.class, clazz);
    }

    /**
     * 字符串转换为Set
     *
     * @param str   待转换的字符串
     * @param clazz 自定义对象
     * @return 返回Set对象
     */
    public static <T> Set<T> toSet(String str, Class<T> clazz) {
        return toCollection(str, Set.class, clazz);
    }

    /**
     * 字符串转换为集合对象
     *
     * @param str  待转换的字符串
     * @param type new TypeReference<T<C>>() {}
     * @return 返回指定的集合对象
     */
    public static <U> U toCollection(String str, TypeReference<U> type) {
        if (StringUtils.isEmpty(str) || null == type) {
            return null;
        }
        try {
            // 如果目标类型是字符串，直接返回原始字符串（类型强转保证一致）
            if (type.getType().equals(String.class)) {
                @SuppressWarnings("unchecked")
                U result = (U) str;
                return result;
            }
            return OBJECT_MAPPER.readValue(str, type);
        } catch (JacksonException e) {
            log.warn("JSON parse failed: {}", str, e); // 推荐加日志
            return null;
        }
    }

    /**
     * 字符串转换为集合对象
     *
     * @param str        待转换的字符串
     * @param collection 集合类型
     * @param elements   对象数组
     * @param <T>
     * @return 返回指定的集合对象
     */
    public static <T> T toCollection(String str, Class<?> collection, Class<?>... elements) {
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(collection, elements);
        try {
            return OBJECT_MAPPER.readValue(str, javaType);
        } catch (JacksonException e) {
            return null;
        }
    }

    /**
     * LocalDateTime序列化策略: LocalDateTime类型序列化为时间戳
     */
    public static class LocalDateTimeSerializer extends ValueSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializationContext ctxt) throws JacksonException {
            jsonGenerator.writeNumber(DateUtil.toLong(localDateTime));
        }
    }

    /**
     * LocalDateTime序列化策略: LocalDateTime类型序列化为 yyyy-MM-dd HH:mm:ss字符串
     */
    public static class LocalDateTimeStringFormatSerializer extends ValueSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializationContext serializerProvider) throws JacksonException {
            jsonGenerator.writeString(DateUtil.toString(localDateTime, STANDARD_FORMAT));
        }
    }

    /**
     * 时间戳反序列化策略：时间戳反序列化LocalDateTime
     */
    public static class LocalDateTimeDeserializer extends ValueDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws JacksonException {
            return DateUtil.toLocalDateTime(jsonParser.getLongValue());
        }
    }

    /**
     * yyyy-MM-dd HH:mm:ss字符串反序列化策略：yyyy-MM-dd HH:mm:ss字符串 反序列化为LocalDateTime类型
     */
    public static class LocalDateTimeStringFormatDeserializer extends ValueDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws JacksonException {
            return DateUtil.toLocalDateTime(jsonParser.getValueAsString());
        }
    }
}
