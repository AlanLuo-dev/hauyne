package com.luoyx.hauyne.swaggerv3_enum.me.swagger;

import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.models.media.Schema;

public class CodeEnumModelConverter extends ModelResolver implements CodeEnumResolver {

    public CodeEnumModelConverter() {
        super(Json.mapper());
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Schema _createSchemaForEnum(Class<Enum<?>> enumClass) {
        Schema schema = super._createSchemaForEnum(enumClass);
        if (this.isCodeEnum(enumClass)) {
            this.fillCodeEnumSchema(schema, enumClass);
        }

        return schema;
    }
}
