package com.luoyx.hauyne.web.yiyiyiyi.swagger;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.models.media.Schema;

import java.util.Iterator;

public class CodeEnumModelConverter extends ModelResolver implements CodeEnumResolver {

    public CodeEnumModelConverter() {
        super(Json.mapper());
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Schema resolve(AnnotatedType type, ModelConverterContext context, Iterator<ModelConverter> next) {
        Schema schema = super.resolve(type, context, next);
        Class<?> enumClass = (Class<?>) type.getType();
        if (this.isCodeEnum(enumClass)) {

            this.fillCodeEnumSchema(schema, enumClass);
        }

        return schema;
    }
}
