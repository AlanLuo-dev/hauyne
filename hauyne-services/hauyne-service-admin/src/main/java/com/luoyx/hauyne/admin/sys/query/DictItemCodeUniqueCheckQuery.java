package com.luoyx.hauyne.admin.sys.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 校验同一个字典类型下，字典值的唯一性 查询参数Query类
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DictItemCodeUniqueCheckQuery {

    /**
     * 排除的字典值id（编辑字典值编码的场景）
     */
    @Schema(description = "排除的字典值id（编辑字典值编码的场景）")
    private Long excludeDictItemId;

    /**
     * 字典类型Id
     */
    @Schema(description = "字典类型Id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "字典类型Id不能为空")
    private Long dictTypeId;

    /**
     * 字典值编码
     */
    @Schema(description = "字典值编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "字典值编码不能为空")
    private String dictItemCode;
}