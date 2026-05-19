package com.luoyx.hauyne.web.enumsupport.jackson;

import com.luoyx.hauyne.api.enumsupport.EnumSpec;
import tools.jackson.databind.BeanDescription;
import tools.jackson.databind.DeserializationConfig;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.deser.ValueDeserializerModifier;

public class EnumSpecDeserializerModifier extends ValueDeserializerModifier {

    @Override
    public ValueDeserializer<?> modifyEnumDeserializer(
            DeserializationConfig config,
            JavaType type,
            BeanDescription.Supplier beanDescRef,
            ValueDeserializer<?> deserializer) {

        Class<?> rawClass = type.getRawClass();

        if (rawClass.isEnum() && EnumSpec.class.isAssignableFrom(rawClass)) {
            return new EnumSpecDeserializer<>((Class) rawClass);
        }

        return deserializer;
    }
}

