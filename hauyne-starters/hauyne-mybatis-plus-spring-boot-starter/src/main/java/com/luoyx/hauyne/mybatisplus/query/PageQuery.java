package com.luoyx.hauyne.mybatisplus.query;


import com.luoyx.hauyne.validation.constraint.EnumCheck;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 分页请求参数类
 *
 * @author 罗英雄
 * @date 2019/5/31 11:52
 */
@Getter
@Setter
public class PageQuery {

    /**
     * 页码
     */
    @Schema(description = "页码", example = "1")
    @NotNull(message = "页码不能为空")
    @Positive(message = "页码必须是大于0的整数")
    private Integer pageIndex;

    /**
     * 每页行数
     */
    @Schema(description = "每页行数", example = "10")
    @NotNull(message = "每页行数不能为空")
    @Max(value = 500, message = "pageSize最大不超过500条")
    private Integer pageSize;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段", example = "id")
    private String sortField;

    /**
     * 排序方式
     */
    @Schema(description = "排序方式（ascend=升序，descend=降序）", example = "descend")
    @EnumCheck(message = "排序方式枚举值不合法", enumClazz = SortOrderEnum.class, getterMethod = "getValue")
    private String sortOrder;

    /**
     * 排序方式枚举类
     */
    @Getter
    @RequiredArgsConstructor
    public enum SortOrderEnum {

        ASC("ascend", "ASC"),
        DESC("descend", "DESC");

        /**
         * 排序方式
         */
        private final String value;

        /**
         * sql排序关键字
         */
        private final String sqlKeyWord;

        /**
         * 排序方式 键值对
         * <p>
         * key: 前端Ng-Zorro传递的排序参数
         * value: sql排序关键字
         */
        public static final Map<String, String> SORT_ORDER_MAP;

        static {
            Map<String, String> sortOrderMap = new HashMap<>();
            for (SortOrderEnum item : SortOrderEnum.values()) {
                sortOrderMap.put(item.getValue(), item.getSqlKeyWord());
            }
            SORT_ORDER_MAP = Collections.unmodifiableMap(sortOrderMap);
        }

        /**
         * 判断参数合法性
         */
        public static boolean validOrder(String key) {
            if (null != key && !"".equals(key.trim())) {
                return SORT_ORDER_MAP.containsKey(key);
            }

            return true;
        }
    }
}
