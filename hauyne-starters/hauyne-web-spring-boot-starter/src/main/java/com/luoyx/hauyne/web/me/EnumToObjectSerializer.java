package com.luoyx.hauyne.web.me;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.io.Serializable;

/**
 * 枚举序列化器：将 BaseEnum 转为嵌套 JSON 对象
 */
public class EnumToObjectSerializer<K extends Serializable, T extends Enum<T> & EnumSchema<K, T>> extends JsonSerializer<EnumSchema<K, T>> {

    @Override
    public void serialize(EnumSchema<K, T> enumValue, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
// 开始序列化对象（生成 { ）
        gen.writeStartObject();
        // 写入 code 字段（值为枚举的 getCode() 结果）
        gen.writeObjectField("value", enumValue.getValue());
        // 写入 desc 字段（值为枚举的 getDesc() 结果）
        gen.writeStringField("label", enumValue.getLabel());
        // 结束序列化对象（生成 } ）
        gen.writeEndObject();
    }

    /**
     * Method for accessing type of Objects this serializer can handle.
     * Note that this information is not guaranteed to be exact -- it
     * may be a more generic (super-type) -- but it should not be
     * incorrect (return a non-related type).
     * <p>
     * Default implementation will return null, which essentially means
     * same as returning <code>Object.class</code> would; that is, that
     * nothing is known about handled type.
     * <p>
     */
    @Override
    @SuppressWarnings("unchecked")
    public Class<EnumSchema<K, T>> handledType() {
        return (Class<EnumSchema<K, T>>) (Class<?>) EnumSchema.class;
    }

}
