package com.luoyx.hauyne.web.enums.springdoc;

import com.fasterxml.jackson.databind.JavaType;
import com.luoyx.hauyne.web.enums.core.EnumSchema;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springdoc.core.customizers.PropertyCustomizer;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CodeEnumPropertyCustomizer implements PropertyCustomizer {

    @Override
    @SuppressWarnings("rawtypes")
    public Schema customize(Schema schema, AnnotatedType annotatedType) {
        if (annotatedType.getType() instanceof JavaType type && type.isEnumType() && isCodeEnum(type.getRawClass())) {
            List<EnumSchema<? extends Serializable, ?>> enumConstants =
                    List.of((EnumSchema<? extends Serializable, ?>[])type.getRawClass().getEnumConstants());

            String description = enumConstants.stream()
                    .map(enumSchema -> enumSchema.getValue() + " = " + enumSchema.getLabel())
                    .collect(Collectors.joining("，", "<b>（", "）</b>"));
            schema.setEnum(enumConstants.stream().map(EnumSchema::getValue).map(Object::toString).toList());
            schema.setDescription(schema.getDescription() + description);
            schema.setExample(enumConstants.stream().map(EnumSchema::getValue).map(Object::toString).findFirst().orElse(null));

            EnumSchema<? extends Serializable, ?> enumConstant = enumConstants.stream().findFirst().orElse(null);
            if (Objects.nonNull(enumConstant)) {
                ObjectSchema enumObjectSchema = new ObjectSchema();

                Schema valueSchema = new StringSchema();
                valueSchema.setExample(enumConstant.getValue().toString()); // 示例值
                enumObjectSchema.addProperty("value", valueSchema); // 加入结构化对象

                // 3. 添加label字段(构建字段Schema并设置示例)
                Schema labelSchema = new StringSchema();
                labelSchema.setExample(enumConstant.getLabel()); // 示例值
                enumObjectSchema.addProperty("label", labelSchema); // 加入结构化对象

                // 4. 复制原有Schema的基础设施(描述、是否必填等，保证兼容性)
                enumObjectSchema.setRequired(schema.getRequired());
                enumObjectSchema.setNullable(schema.getNullable());
                enumObjectSchema.setDescription(schema.getDescription());

                return enumObjectSchema;
            }

        }
        return schema;
    }

    private boolean isCodeEnum(Class<?> rawClass) {
        if (rawClass == null || !rawClass.isEnum()) {
            return false;
        }
        // 直接判断 rawClass 是否实现了 EnumSchema 接口(更直接、更可靠)
        return EnumSchema.class.isAssignableFrom(rawClass);
    }

}
