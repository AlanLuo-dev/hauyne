package com.luoyx.hauyne.admin.sys.query;

import com.luoyx.hauyne.admin.sys.enums.ColorEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EnumInQuery {

    @Schema(description = "颜色")
    private ColorEnum color;
}
