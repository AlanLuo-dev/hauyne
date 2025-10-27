package com.luoyx.hauyne.toolbox.codegen.vo.response;

import com.luoyx.hauyne.commons.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 数据源VO类
 *
 * @author 1564469545@qq.com
 * @date 2023/3/31 21:29
 */
@Getter
@Setter
@ToString
@SuppressWarnings("serial")
public class DataSourcePageResultVO extends BaseVO {

    /**
     * 数据源名称
     */
    @Schema(description = "数据源名称")
    private String dataSourceName;

    /**
     * JDBC驱动类名
     */
    @Schema(description = "JDBC驱动类名")
    private String driverClassName;

    /**
     * JDBC连接URL
     */
    @Schema(description = "JDBC连接URL")
    private String url;

    /**
     * 数据库用户名
     */
    @Schema(description = "数据库用户名")
    private String username;

    /**
     * 数据库密码
     */
    @Schema(description = "数据库密码")
    private String password;
}
