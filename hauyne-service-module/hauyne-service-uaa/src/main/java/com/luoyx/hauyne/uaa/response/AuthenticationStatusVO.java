package com.luoyx.hauyne.uaa.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AuthenticationStatusVO {

    @Schema(description = "是否已认证")
    private boolean authenticated;

    @Schema(description = "当前用户信息")
    private Object principal;

}
