package com.luoyx.hauyne.toolbox.codegen.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

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
public class DataSourcePageResultVO {

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

    /**
     * 创建人id
     */
    @JsonIgnore
    private Long createdBy;

    /**
     * 创建人的真实姓名
     */
    @Schema(description = "创建人的真实姓名")
    private String createdByFullName;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    /**
     * 最后修改人id
     */
    @JsonIgnore
    private Long lastUpdatedBy;

    /**
     * 最后修改人的真实姓名
     */
    @Schema(description = "最后修改人的真实姓名")
    private String lastModifiedByFullName;

    /**
     * 最后修改时间
     */
    @Schema(description = "最后修改时间")
    private LocalDateTime lastUpdatedTime;
}
