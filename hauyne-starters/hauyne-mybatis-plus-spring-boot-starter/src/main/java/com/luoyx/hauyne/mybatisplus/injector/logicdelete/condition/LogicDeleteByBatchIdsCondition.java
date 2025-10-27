package com.luoyx.hauyne.mybatisplus.injector.logicdelete.condition;

import lombok.Getter;

import java.io.Serializable;
import java.util.Collection;

/**
 * 逻辑删除条件：批量的主键ID
 *
 * @author luo_yingxiong@163.com
 */
@Getter
public class LogicDeleteByBatchIdsCondition extends LogicDeleteAuditValue {

    private final Collection<? extends Serializable> ids;

    public LogicDeleteByBatchIdsCondition(Collection<? extends Serializable> ids) {
        this.ids = ids;
    }
}
