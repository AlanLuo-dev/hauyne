package com.luoyx.hauyne.swaggerv3_enum.me.swagger;

import com.fasterxml.jackson.databind.JavaType;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.customizers.PropertyCustomizer;

public class CodeEnumPropertyCustomizer implements PropertyCustomizer, CodeEnumResolver {

    @Override
    @SuppressWarnings("rawtypes")
    public Schema customize(Schema schema, AnnotatedType annotatedType) {
        if (annotatedType.getType() instanceof JavaType type && type.isEnumType() && isCodeEnum(type.getRawClass())) {
            this.fillCodeEnumSchema(schema, type.getRawClass());
        }
        return schema;
    }
}
