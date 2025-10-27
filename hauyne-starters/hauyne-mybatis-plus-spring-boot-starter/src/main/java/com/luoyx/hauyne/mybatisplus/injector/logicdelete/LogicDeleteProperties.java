package com.luoyx.hauyne.mybatisplus.injector.logicdelete;

import com.luoyx.hauyne.mybatisplus.injector.logicdelete.method.LogicDeleteById;
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
@ConfigurationProperties(prefix = "hauyne.logic-delete")
public class LogicDeleteProperties {

    /**
     * 归档表的表名前缀，默认为: _deleted_
     */
    private String deletedTableNamePrefix = "_deleted_";

    /**
     * 归档表中记录删除者id的字段名，默认为: deleted_by
     */
    private String deletedByColumnName = "deleted_by";

    /**
     * 归档表中记录删除时间的字段名，默认为: deleted_time
     */
    private String deletedTimeColumnName = "deleted_time";
}
