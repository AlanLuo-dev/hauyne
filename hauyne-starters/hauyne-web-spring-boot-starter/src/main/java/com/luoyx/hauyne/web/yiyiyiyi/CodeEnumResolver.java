package com.luoyx.hauyne.web.yiyiyiyi;

import io.swagger.v3.oas.models.media.Schema;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface CodeEnumResolver {

    @SuppressWarnings({"unchecked", "rawtypes"})
    default void fillCodeEnumSchema(Schema schema, Class<?> rawClass) {
        List<CodeEnum<Serializable>> enumConstants = List.of((CodeEnum<Serializable>[]) rawClass.getEnumConstants());
        String description = enumConstants.stream()
                .map(codeEnum -> codeEnum.getCode() + ":" + codeEnum.getMsg())
                .collect(Collectors.joining(",", "[", "]"));

        schema.setEnum(enumConstants.stream().map(CodeEnum::getCode).map(Object::toString).toList());
        schema.setExample(enumConstants.stream().map(CodeEnum::getCode).map(Object::toString).findFirst().orElse(null));
        schema.setDescription(Optional.ofNullable(schema.getDescription()).orElse(StringUtils.EMPTY) + description);
    }

    default boolean isNotCodeEnum(Class<?> rawClass) {
        return !this.isCodeEnum(rawClass);
    }

    default boolean isCodeEnum(Class<?> rawClass) {
        if (!rawClass.isEnum()) {
            return false;
        }

        for (Class<?> anInterface : rawClass.getInterfaces()) {
            if (anInterface.isAssignableFrom(CodeEnum.class)) {
                return true;
            }
        }

        return false;
    }
}
