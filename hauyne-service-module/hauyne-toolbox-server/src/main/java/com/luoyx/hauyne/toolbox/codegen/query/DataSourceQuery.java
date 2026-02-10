package com.luoyx.hauyne.toolbox.codegen.query;

import com.luoyx.hauyne.jpa.query.PrimeNgPageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 数据源配置 过滤条件
 *
 * @author 1564469545@qq.com
 * @date 2023/4/1 10:25
 */
@Getter
@Setter
@ToString
public class DataSourceQuery extends PrimeNgPageQuery {

    /**
     * 数据源名称
     */
    @Schema(description = "数据源名称(模糊条件)")
    private String dataSourceName;

    /**
     * 创建人id
     */
    @Schema(description = "创建人id")
    private Long createdBy;


}
