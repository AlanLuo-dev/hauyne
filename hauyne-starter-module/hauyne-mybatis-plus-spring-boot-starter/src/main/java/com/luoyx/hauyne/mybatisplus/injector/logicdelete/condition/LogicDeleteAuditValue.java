package com.luoyx.hauyne.mybatisplus.injector.logicdelete.condition;

import com.luoyx.hauyne.security.context.UserContextHolder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 逻辑删除审计字段值
 *
 * @author luo_yingxiong@163.com
 */
@Getter
public class LogicDeleteAuditValue {

    /**
     * 删除者id
     */
    private final Long deletedBy;

    /**
     * 删除时间
     */
    private final LocalDateTime deletedTime;

    public LogicDeleteAuditValue() {

        // 设置为当前用户id、当前时间
        this.deletedBy = Optional.ofNullable(UserContextHolder.getUserId()).orElse(0L);
        this.deletedTime = LocalDateTime.now();
    }
}
