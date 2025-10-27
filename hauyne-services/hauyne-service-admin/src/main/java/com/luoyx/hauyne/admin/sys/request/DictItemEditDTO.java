package com.luoyx.hauyne.admin.sys.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 修改字典 表单参数VO 类
 */
@Getter
@Setter
@ToString
public class DictItemEditDTO extends DictItemCreateDTO {

    /**
     * id
     */
    @Schema(description = "Id")
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 是否启用
     */
    @Schema(description = "是否启用【true=启用；false=禁用】")
    @NotNull(message = "是否启用不能为空")
    private Boolean enabled;
}
