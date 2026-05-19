package com.luoyx.hauyne.web.enumsupport.jackson;

import com.luoyx.hauyne.api.enumsupport.EnumSpec;
import com.luoyx.hauyne.web.exception.InvalidEnumValueException;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ValueDeserializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;


public class EnumSpecDeserializer<R extends Enum<R> & EnumSpec<? extends Serializable, R>> extends ValueDeserializer<R> {

    private final Class<R> enumType;

    public EnumSpecDeserializer(Class<R> enumType) {
        this.enumType = enumType;
    }

    @Override
    public R deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
        JsonNode node = ctxt.readTree(p);
        Serializable inputValue;
        if (node.isObject() && node.has("value")) {
            JsonNode valueNode = node.get("value");
            inputValue = readNodeValue(valueNode);
        } else if (node.isValueNode()) {
            inputValue = readNodeValue(node);
        } else {
            return null;
        }

        for (R e : enumType.getEnumConstants()) {
            final Serializable enumValue = e.getValue();
            if (enumValue instanceof BigDecimal && inputValue instanceof Number) {
                if (((BigDecimal) enumValue).compareTo(new BigDecimal(inputValue.toString())) == 0){
                    return e;
                }
            }

            if (Objects.equals(enumValue, inputValue)) {
                return e;
            }
        }

        // 建议抛异常，而不是 silent null
//        throw ctxt.weirdStringException(
//                String.valueOf(inputValue),
//                enumType,
//                "Unknown enum value for " + enumType.getSimpleName()
//        );

        // ⭐ 获取字段名
        throw new InvalidEnumValueException(String.valueOf(inputValue), enumType);
    }

    private Serializable readNodeValue(JsonNode node) {
        if (node.isInt()) {
            return node.intValue();
        }
        if (node.isLong()) {
            return node.longValue();
        }
        if (node.isString()) {
            return node.stringValue();
        }
        if (node.isBoolean()) {
            return node.booleanValue();
        }
        if (node.isBigDecimal()) {
            return node.decimalValue();
        }
        if (node.isNumber()) {
            return node.numberValue();
        }
        return node.asString();
    }
}
