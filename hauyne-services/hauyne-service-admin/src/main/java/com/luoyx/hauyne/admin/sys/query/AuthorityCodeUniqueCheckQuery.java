package com.luoyx.hauyne.admin.sys.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 权限编码 唯一性校验 查询条件
 *
 * @author Alan.Luo
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityCodeUniqueCheckQuery {

    /**
     * 要排除的权限id
     */
    @Schema(description = "要排除的权限id")
    private Long excludeAuthorityId;

    /**
     * 权限编码
     */
    @Schema(description = "权限编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "权限编码必填")
    private String authorityCode;
}
