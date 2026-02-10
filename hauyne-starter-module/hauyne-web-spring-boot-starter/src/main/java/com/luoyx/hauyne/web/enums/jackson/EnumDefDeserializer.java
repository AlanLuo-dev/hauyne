package com.luoyx.hauyne.web.enums.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.luoyx.hauyne.api.enums.core.EnumDefinition;
import com.luoyx.hauyne.web.exception.InvalidEnumValueException;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;


public class EnumDefDeserializer<R extends Enum<R> & EnumDefinition<? extends Serializable, R>> extends JsonDeserializer<R> {

    private final Class<R> enumType;

    public EnumDefDeserializer(Class<R> enumType) {
        this.enumType = enumType;
    }

    @Override
    public R deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
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
        if (node.isTextual()) {
            return node.textValue();
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
        return node.asText();
    }
}
