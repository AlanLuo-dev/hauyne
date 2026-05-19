package com.luoyx.hauyne.web.enumsupport.jackson;

import com.luoyx.hauyne.api.enumsupport.EnumSpec;
import tools.jackson.databind.BeanDescription;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.SerializationConfig;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.ser.ValueSerializerModifier;

public class EnumSpecSerializerModifier extends ValueSerializerModifier {

    @Override
    public ValueSerializer<?> modifyEnumSerializer(
            SerializationConfig config,
            JavaType valueType,
            BeanDescription.Supplier beanDesc,
            ValueSerializer<?> serializer) {

        Class<?> rawClass = valueType.getRawClass();

        if (EnumSpec.class.isAssignableFrom(rawClass)) {
            return new EnumSpecSerializer<>();
        }

        return serializer;
    }
}

