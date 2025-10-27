package com.luoyx.hauyne.audit.api.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 审计类型 枚举
 *
 * @author Alan.Luo
 */
@Getter
@RequiredArgsConstructor
public enum AuditTypeEnum {

    /**
     * 提交
     */
    COMMIT("commit", "提交"),

    /**
     * 删除
     */
    SHALLOW_DELETE("commitShallowDelete", "删除");

    private final String value;
    private final String name;
}
