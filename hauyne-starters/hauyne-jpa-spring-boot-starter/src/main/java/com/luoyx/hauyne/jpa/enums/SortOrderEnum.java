package com.luoyx.hauyne.jpa.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 排序方式枚举类
 *
 * @author Alan.Luo
 */
@Getter
@AllArgsConstructor
public enum SortOrderEnum {

    /**
     * 升序
     */
    ASC(1, "升序", "ASC"),

    /**
     * 降序
     */
    DESC(-1, "降序", "DESC");

    // ----------------------------------------- //

    /**
     * 前端传来的排序方式字段值
     */
    private final Integer frontEndSortOrder;

    /**
     * 描述
     */
    private final String description;

    /**
     * sql关键字
     */
    private final String sqlKeyWord;


    /**
     * 判断参数合法性
     */
    public static boolean validOrder(Integer frontEndSortOrder) {
        if (null != frontEndSortOrder) {
            for (SortOrderEnum orderEnum : SortOrderEnum.values()) {
                if (orderEnum.frontEndSortOrder.equals(frontEndSortOrder)) {
                    return true;
                }
            }
            return false;
        }

        return true;
    }

    public static String getSqlKeyword(Integer frontEndSortOrder) {
        for (SortOrderEnum orderEnum : SortOrderEnum.values()) {
            if (orderEnum.frontEndSortOrder.equals(frontEndSortOrder)) {
                return orderEnum.sqlKeyWord;
            }
        }

        return null;
    }
}
