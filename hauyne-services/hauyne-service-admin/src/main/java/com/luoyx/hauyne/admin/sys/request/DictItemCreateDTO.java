package com.luoyx.hauyne.admin.sys.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * 新增字典 表单参数VO 类
 *
 * @author luoyingxiong
 */
@Getter
@Setter
@ToString
public class DictItemCreateDTO {

    /**
     * 字典类型id
     */
    @Schema(description = "字典类型id")
    @NotNull(message = "字典类型不能为空")
    private Long dictTypeId;

    /**
     * 字典选项编码
     */
    @Schema(description = "字典选项编码")
    @NotBlank(message = "字典选项编码不能为空")
    @Length(max = 50, message = "字典选项编码不能超过50个字")
    private String dictItemCode;

    /**
     * 字典选项名称
     */
    @Schema(description = "字典选项名称")
    @NotBlank(message = "字典选项名称不能为空")
    @Length(max = 50, message = "字典选项名称不能超过50个字")
    private String dictItemName;

    /**
     * 序号
     */
    @Schema(description = "序号")
    @NotNull(message = "序号不能为空")
    @Min(value = 1, message = "序号不能小于1")
    @Max(value = Short.MAX_VALUE, message = "序号不能大于" + Short.MAX_VALUE)
    private Integer sort;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Length(max = 60, message = "备注不能超过60个字")
    private String remark;
}
