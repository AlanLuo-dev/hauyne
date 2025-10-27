package com.luoyx.hauyne.toolbox.codegen.entity;

import com.luoyx.hauyne.jpa.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * <p>
 * 数据源配置表
 * </p>
 *
 * @author 1564469545@qq.com
 * @since 2023-03-31
 */

@Data
@Entity
@Table(name = "hyn_codegen_data_source")
@DynamicInsert
@DynamicUpdate
public class DataSource extends BaseEntity {


    /**
     * 数据源名称
     */
    @Column(name = "data_source_name")
    private String dataSourceName;

    /**
     * JDBC驱动类名
     */
    @Column(name = "driver_class_name")
    private String driverClassName;

    /**
     * JDBC连接URL
     */
    @Column(name = "url")
    private String url;

    /**
     * 数据库用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 数据库密码
     */
    @Column(name = "password")
    private String password;
}
