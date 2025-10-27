package com.luoyx.hauyne.toolbox.codegen.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

/**
 * 新增数据源 表单VO类
 *
 * @author 1564469545@qq.com
 * @date 2023/3/31 21:29
 */
@Getter
@Setter
@ToString
public class DataSourceAddFormVO {

    /**
     * 数据源名称
     */
    @Schema(description = "数据源名称")
    @NotBlank(message = "请填写数据源名称")
    @Length(max = 50, message = "数据源名称最多只能50个字符")
    private String dataSourceName;

    /**
     * JDBC驱动类名
     */
    @Schema(description = "JDBC驱动类名")
    @NotBlank(message = "请填写JDBC驱动类名")
    @Length(max = 100, message = "JDBC驱动类名最多只能100个字符")
    private String driverClassName;

    /**
     * JDBC连接URL
     */
    @Schema(description = "JDBC连接URL")
    @NotBlank(message = "请填写")
    @Length(max = 500, message = "JDBC连接URL最多只能500个字符")
    private String url;

    /**
     * 数据库用户名
     */
    @Schema(description = "数据库用户名")
    @NotBlank(message = "请填写数据库用户名")
    @Length(max = 100, message = "数据库用户名最多只能100个字符")
    private String username;

    /**
     * 数据库密码
     */
    @Schema(description = "数据库密码")
    @NotBlank(message = "请填写数据库密码")
    @Length(max = 100, message = "数据库密码最多只能100个字符")
    private String password;
}
