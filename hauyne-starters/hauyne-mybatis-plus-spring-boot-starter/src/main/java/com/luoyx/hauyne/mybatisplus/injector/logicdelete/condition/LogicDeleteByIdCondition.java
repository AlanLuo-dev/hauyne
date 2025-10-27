package com.luoyx.hauyne.mybatisplus.injector.logicdelete.condition;

import lombok.Getter;

import java.io.Serializable;

/**
 * 逻辑删除条件：主键ID
 *
 * @author luo_yingxiong@163.com
 */
@Getter
public class LogicDeleteByIdCondition extends LogicDeleteAuditValue {

    private final Serializable id;

    public LogicDeleteByIdCondition(Serializable id) {
        this.id = id;
    }
}
