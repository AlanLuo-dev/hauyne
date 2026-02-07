package com.luoyx.hauyne.admin.api.sys.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginLookupQuery {

    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(description = "时间戳", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long timestamp;

    @Schema(description = "签名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String sign;
}
