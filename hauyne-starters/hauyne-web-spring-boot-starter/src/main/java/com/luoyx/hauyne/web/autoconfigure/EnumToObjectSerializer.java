package com.luoyx.hauyne.web.autoconfigure;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.io.Serializable;

/**
 * 枚举序列化器：将 BaseEnum 转为嵌套 JSON 对象
 */
public class EnumToObjectSerializer<T extends Serializable> extends JsonSerializer<BaseEnum<T>> {

    @Override
    public void serialize(BaseEnum<T> enumValue, JsonGenerator gen, SerializerProvider serializers) throws IOException {
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
    public Class<BaseEnum<T>> handledType() {
        return (Class<BaseEnum<T>>) (Class<?>) BaseEnum.class;
    }

}
