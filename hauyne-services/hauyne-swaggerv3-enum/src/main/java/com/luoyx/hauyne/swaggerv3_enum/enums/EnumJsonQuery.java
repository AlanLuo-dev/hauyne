package com.luoyx.hauyne.swaggerv3_enum.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EnumJsonQuery {

    @Schema(description = "参数")
    private TestCodeEnum testCodeEnum;

    @Schema(description = "参数2")
    private String param2;
}
