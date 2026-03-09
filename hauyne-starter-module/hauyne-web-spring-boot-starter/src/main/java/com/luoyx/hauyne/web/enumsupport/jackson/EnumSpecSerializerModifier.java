package com.luoyx.hauyne.web.enumsupport.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.luoyx.hauyne.api.enumdef.EnumSpec;

public class EnumSpecSerializerModifier extends BeanSerializerModifier {

    @Override
    public JsonSerializer<?> modifyEnumSerializer(
            SerializationConfig config,
            JavaType valueType,
            BeanDescription beanDesc,
            JsonSerializer<?> serializer) {

        Class<?> rawClass = valueType.getRawClass();

        if (EnumSpec.class.isAssignableFrom(rawClass)) {
            return new EnumSpecSerializer<>();
        }

        return serializer;
    }
}

