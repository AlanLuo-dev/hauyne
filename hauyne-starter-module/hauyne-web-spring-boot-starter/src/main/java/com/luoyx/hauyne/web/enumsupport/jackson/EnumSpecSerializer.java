package com.luoyx.hauyne.web.enumsupport.jackson;

import com.luoyx.hauyne.api.enumsupport.EnumSpec;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

import java.io.Serializable;

/**
 * 枚举序列化器：将 BaseEnum 转为嵌套 JSON 对象
 */
public class EnumSpecSerializer<K extends Serializable, T extends Enum<T> & EnumSpec<K, T>> extends JsonSerializer<EnumSpec<K, T>> {

    @Override
    public void serialize(EnumSpec<K, T> enumValue, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
        gen.writeStartObject();                                           // 开始序列化对象（生成 { ）
        gen.writePOJOProperty("value", enumValue.getValue());    // 写入 code 字段（值为枚举的 getCode() 结果）
        gen.writeStringProperty("label", enumValue.getLabel());    // 写入 desc 字段（值为枚举的 getDesc() 结果）
        gen.writeEndObject();                                             // 结束序列化对象（生成 } ）
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
    public Class<EnumSpec<K, T>> handledType() {
        return (Class<EnumSpec<K, T>>) (Class<?>) EnumSpec.class;
    }

}
