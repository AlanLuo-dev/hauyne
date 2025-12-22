package com.luoyx.hauyne.web.enums.springdoc;

import com.luoyx.hauyne.web.enums.core.EnumSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.ParameterCustomizer;
import org.springframework.core.MethodParameter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 为简单参数（query/path/header）中的 CodeEnum 添加 enum 列表 & 描述
 */
public class EnumParameterCustomizer implements ParameterCustomizer {

    @Override
    public Parameter customize(Parameter parameterModel, MethodParameter methodParameter) {
        if (parameterModel == null || methodParameter == null) {
            return parameterModel;
        }

        // 获取参数的真实类型（对于普通的 @RequestParam TestCodeEnum 这种场景，这里直接是枚举类）
        Class<?> rawClass = methodParameter.getParameterType();

        // 处理数组或集合（如果你有需要，也可以扩展以处理 List<Enum> 等）
        if (rawClass.isArray()) {
            rawClass = rawClass.getComponentType();
        }

        // 仅当它是我们定义的 CodeEnum（实现了 EnumSchema）时才填充 schema
        if (isCodeEnum(rawClass)) {
            Schema schema = parameterModel.getSchema();
            if (schema == null) {
                schema = new Schema<>();
                parameterModel.setSchema(schema);
            }

            List<EnumSchema<? extends Serializable,?>> enumConstants = List.of((EnumSchema<? extends Serializable,?>[]) rawClass.getEnumConstants());

            String enumValues = enumConstants.stream()
                    .map(enumSchema -> enumSchema.getValue() + " = " + enumSchema.getLabel())
                    .collect(Collectors.joining("，", "<b>（", "）</b>"));
            parameterModel.setDescription(parameterModel.getDescription() + enumValues);

            List<String> enumValuesList = new ArrayList<>(enumConstants.size());
            for (EnumSchema<? extends Serializable,?> item : enumConstants) {
                enumValuesList.add(String.valueOf(item.getValue()));
            }
            schema.setEnum(enumValuesList);
            schema.setExample(enumValuesList.isEmpty() ? null : enumValuesList.get(0));
            parameterModel.setExample(schema.getExample());
        }

        return parameterModel;
    }

    private boolean isCodeEnum(Class<?> rawClass) {
        if (rawClass == null || !rawClass.isEnum()) {
            return false;
        }
        // 直接判断 rawClass 是否实现了 EnumSchema 接口（更直接、更可靠）
        return EnumSchema.class.isAssignableFrom(rawClass);
    }
}