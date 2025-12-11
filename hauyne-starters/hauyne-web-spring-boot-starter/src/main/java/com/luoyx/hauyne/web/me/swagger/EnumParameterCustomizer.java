package com.luoyx.hauyne.web.me.swagger;

import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.ParameterCustomizer;
import org.springframework.core.MethodParameter;

import java.util.Optional;

/**
 * 为简单参数（query/path/header）中的 CodeEnum 添加 enum 列表 & 描述
 */
public class EnumParameterCustomizer implements ParameterCustomizer, CodeEnumResolver {

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
            Schema<?> schema = parameterModel.getSchema();
            if (schema == null) {
                schema = new Schema<>();
                parameterModel.setSchema(schema);
            }

            String modelDescription = Optional.ofNullable(parameterModel.getDescription()).orElse("");
            String schemaDescription = schema.getDescription();
            if (!modelDescription.contains(schemaDescription )) {
                parameterModel.setDescription(modelDescription + schemaDescription );
            }
            parameterModel.setExample(schema.getExample());
        }

        return parameterModel;
    }
}