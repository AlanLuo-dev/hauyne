package com.luoyx.hauyne.web.enums.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.luoyx.hauyne.web.enums.core.EnumDef;

public class EnumDefSerializerModifier extends BeanSerializerModifier {

    @Override
    public JsonSerializer<?> modifyEnumSerializer(
            SerializationConfig config,
            JavaType valueType,
            BeanDescription beanDesc,
            JsonSerializer<?> serializer) {

        Class<?> rawClass = valueType.getRawClass();

        if (EnumDef.class.isAssignableFrom(rawClass)) {
            return new EnumDefSerializer<>();
        }

        return serializer;
    }
}

