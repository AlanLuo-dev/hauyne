package com.luoyx.hauyne.swaggerv3_enum.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EnumInQuery {

    @Schema(description = "颜色")
    private ColorEnum color;
}
