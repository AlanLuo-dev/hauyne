package com.luoyx.hauyne.usersnapshot.mapper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 逻辑删除的配置属性
 *
 * @author luo_yingxiong@163.com
 * @since 0.2.0
 *
 * @see LogicDeleteById
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "hauyne.user-snapshot")
public class UserSnapshotProperties {

    private String tableName = "table-name";

}
