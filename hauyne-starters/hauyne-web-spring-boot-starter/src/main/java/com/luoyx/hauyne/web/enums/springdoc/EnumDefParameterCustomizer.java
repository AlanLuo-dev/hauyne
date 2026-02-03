package com.luoyx.hauyne.web.enums.springdoc;

import com.luoyx.hauyne.web.enums.core.EnumDef;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.ParameterCustomizer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 为简单参数（query/path/header）中的 CodeEnum 添加 enum 列表 & 描述
 */
public class EnumDefParameterCustomizer implements ParameterCustomizer {

    @Override
    public Parameter customize(Parameter parameterModel, MethodParameter methodParameter) {
        if (parameterModel == null || methodParameter == null) {
            return parameterModel;
        }

        // 获取参数的真实类型（对于普通的 @RequestParam TestCodeEnum 这种场景，这里直接是枚举类）
        Class<?> enumClass = resolveEnumClass(methodParameter);
        if (enumClass == null || !isCodeEnum(enumClass)) {
            return parameterModel;
        }

        Schema schema = parameterModel.getSchema();
        if (schema == null) {
            schema = new Schema<>();
            parameterModel.setSchema(schema);
        }

        @SuppressWarnings("unchecked")
        List<EnumDef<? extends Serializable, ?>> enumConstants =
                List.of((EnumDef<? extends Serializable, ?>[]) enumClass.getEnumConstants());

        String enumValuesDesc = enumConstants.stream()
                .map(e -> e.getValue() + " = " + e.getLabel())
                .collect(Collectors.joining("，", "<b>（", "）</b>"));

        parameterModel.setDescription(
                (parameterModel.getDescription() == null ? "" : parameterModel.getDescription())
                        + enumValuesDesc
        );

        List<String> enumValues = enumConstants.stream()
                .map(e -> String.valueOf(e.getValue()))
                .toList();

        schema.setEnum(enumValues);
        schema.setExample(enumValues.isEmpty() ? null : enumValues.get(0));
        parameterModel.setExample(schema.getExample());

        return parameterModel;
    }

    private boolean isCodeEnum(Class<?> rawClass) {
        if (rawClass == null || !rawClass.isEnum()) {
            return false;
        }
        // 直接判断 rawClass 是否实现了 EnumSchema 接口（更直接、更可靠）
        return EnumDef.class.isAssignableFrom(rawClass);
    }

    private Class<?> resolveEnumClass(MethodParameter methodParameter) {
        ResolvableType type = ResolvableType.forMethodParameter(methodParameter);

        // 1️⃣ 普通参数：Enum
        Class<?> raw = type.getRawClass();
        if (raw != null && raw.isEnum()) {
            return raw;
        }

        // 2️⃣ 数组：Enum[]
        if (raw != null && raw.isArray()) {
            Class<?> component = raw.getComponentType();
            if (component.isEnum()) {
                return component;
            }
        }

        // 3️⃣ 集合：List<Enum> / Set<Enum>
        if (raw != null && Collection.class.isAssignableFrom(raw)) {
            Class<?> generic = type.getGeneric(0).resolve();
            if (generic != null && generic.isEnum()) {
                return generic;
            }
        }

        return null;
    }

}