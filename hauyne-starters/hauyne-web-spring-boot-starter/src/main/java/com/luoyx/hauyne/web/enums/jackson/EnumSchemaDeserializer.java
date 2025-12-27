package com.luoyx.hauyne.web.enums.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.luoyx.hauyne.web.enums.core.EnumSchema;

import java.io.IOException;
import java.util.Objects;

//public class EnumSchemaDeserializer<K extends Serializable, T extends Enum<T> & EnumSchema<K, T>> extends JsonDeserializer<EnumSchema<K, T>>
//        implements ContextualDeserializer {

//    private Class<?> targetEnum;
//
//    public EnumSchemaDeserializer() {
//    }
//
//    private EnumSchemaDeserializer(Class<?> targetEnum) {
//        this.targetEnum = targetEnum;
//    }




//    @Override
//    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
//
//        JavaType type = property != null
//                ? property.getType()
//                : ctxt.getContextualType();
//
//        if (type != null && type.getRawClass().isEnum()) {
//            return new EnumSchemaDeserializer(type.getRawClass());
//        }
//
//        return this;
//    }


//    @Override
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    public EnumSchema<?, ?> deserialize(JsonParser p, DeserializationContext ctxt)
//            throws IOException {
//        JsonNode node = p.getCodec().readTree(p);
//
//        Object value;
//        if (node.isObject() && node.has("value")) {
//            value = node.get("value").asText();
//        } else {
//            value = node.asText();
//        }
//
//        for (Object enumConstant : targetEnum.getEnumConstants()) {
//            EnumSchema schema = (EnumSchema) enumConstant;
//            if (Objects.equals(String.valueOf(schema.getValue()), value)) {
//                return schema;
//            }
//        }
//        return null;
//    }
//}


public class EnumSchemaDeserializer
        extends JsonDeserializer<Enum<?>> {

    private final Class<? extends Enum<?>> enumType;

    public EnumSchemaDeserializer(Class<? extends Enum<?>> enumType) {
        this.enumType = enumType;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Enum<?> deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {

        JsonNode node = p.getCodec().readTree(p);
        String input;

        // {"value": "red"}
        if (node.isObject() && node.has("value")) {
            input = node.get("value").asText();
        }
        // "red"
        else if (node.isValueNode()) {
            input = node.asText();
        } else {
            return null;
        }

        for (Enum<?> e : enumType.getEnumConstants()) {
            EnumSchema schema = (EnumSchema) e;
            if (Objects.equals(schema.getValue(), input)) {
                return e;
            }
        }

        return null;
    }
}
