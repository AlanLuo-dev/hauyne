package com.luoyx.hauyne.web.me.swagger;

import com.luoyx.hauyne.web.me.EnumSchema;
import io.swagger.v3.oas.models.media.Schema;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface CodeEnumResolver {

    @SuppressWarnings({"unchecked", "rawtypes"})
    default void fillCodeEnumSchema(Schema schema, Class<?> rawClass) {
        List<EnumSchema<? extends Serializable,?>> enumConstants = List.of((EnumSchema<? extends Serializable,?>[]) rawClass.getEnumConstants());

        String description = enumConstants.stream()
                .map(codeEnum -> codeEnum.getValue() + " = " + codeEnum.getLabel())
                .collect(Collectors.joining("，", "<b>（", "）</b>"));

        schema.setEnum(enumConstants.stream().map(EnumSchema::getValue).map(Object::toString).toList());
        schema.setExample(enumConstants.stream().map(EnumSchema::getValue).map(Object::toString).findFirst().orElse(null));
        schema.setDescription(Optional.ofNullable(schema.getDescription()).orElse(StringUtils.EMPTY) + description);
    }

    default boolean isNotCodeEnum(Class<?> rawClass) {
        return !this.isCodeEnum(rawClass);
    }

    default boolean isCodeEnum(Class<?> rawClass) {
        if (rawClass == null || !rawClass.isEnum()) {
            return false;
        }
        // 直接判断 rawClass 是否实现了 EnumSchema 接口（更直接、更可靠）
        return EnumSchema.class.isAssignableFrom(rawClass);
    }
}
