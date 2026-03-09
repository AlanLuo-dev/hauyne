package com.luoyx.hauyne.web.enumsupport.springdoc;

import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.models.media.Schema;

public class EnumSpecModelConverter extends ModelResolver implements EnumSpecResolver {

    public EnumSpecModelConverter() {
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
