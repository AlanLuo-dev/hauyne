package com.luoyx.hauyne.admin.sys.query;

import com.luoyx.hauyne.admin.api.sys.enums.TestCodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class JsonQuery {

    @Schema(description = "枚举参数1")
    private TestCodeEnum testCodeEnum;
}
