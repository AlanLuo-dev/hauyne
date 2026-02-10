package com.luoyx.hauyne.toolbox.codegen.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;

/**
 * 修改数据源 表单VO类
 *
 * @author 1564469545@qq.com
 * @date 2023/3/31 21:29
 */
@Getter
@Setter
@ToString
public class DataSourceEditFormVO extends DataSourceAddFormVO{

    /**
     * 主键
     */
    @Schema(description = "Id")
    @NotNull(message = "id不能为空")
    private Long id;
}
