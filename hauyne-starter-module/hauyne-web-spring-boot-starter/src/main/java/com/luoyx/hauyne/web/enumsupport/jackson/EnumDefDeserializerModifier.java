package com.luoyx.hauyne.web.enumsupport.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.luoyx.hauyne.api.enumdef.EnumSpec;

public class EnumDefDeserializerModifier extends BeanDeserializerModifier {

    @Override
    public JsonDeserializer<?> modifyEnumDeserializer(
            DeserializationConfig config,
            JavaType type,
            BeanDescription beanDesc,
            JsonDeserializer<?> deserializer) {

        Class<?> rawClass = type.getRawClass();

        if (rawClass.isEnum() && EnumSpec.class.isAssignableFrom(rawClass)) {
            return new EnumDefDeserializer<>((Class) rawClass);
        }

        return deserializer;
    }
}

