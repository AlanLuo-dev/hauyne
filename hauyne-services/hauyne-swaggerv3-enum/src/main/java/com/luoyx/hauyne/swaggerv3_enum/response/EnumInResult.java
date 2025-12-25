package com.luoyx.hauyne.swaggerv3_enum.response;

import com.luoyx.hauyne.swaggerv3_enum.request.ColorEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EnumInResult {

    @Schema(description = "颜色")
    private ColorEnum color;
}
