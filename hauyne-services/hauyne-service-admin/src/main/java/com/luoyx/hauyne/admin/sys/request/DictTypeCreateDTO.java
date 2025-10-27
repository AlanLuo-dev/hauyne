package com.luoyx.hauyne.admin.sys.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 数据字典类型 新增表单数据封装 VO类
 *
 * @author LuoYingxiong
 */
@Getter
@Setter
@ToString
public class DictTypeCreateDTO {

    /**
     * 字典类型编码
     */
    @Schema(description = "字典类型编码")
    @NotBlank(message = "字典类型编码不能为空")
    private String dictTypeCode;

    /**
     * 字典类型名称
     */
    @Schema(description = "字典类型名称")
    @NotBlank(message = "字典类型名称不能为空")
    private String dictTypeName;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String description;
}
