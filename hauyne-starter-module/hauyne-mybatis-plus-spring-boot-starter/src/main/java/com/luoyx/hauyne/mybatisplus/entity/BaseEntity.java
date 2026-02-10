package com.luoyx.hauyne.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * BaseEntity 基类
 *
 * @param <T>
 * @author Alan.Luo
 */
@Getter
@Setter
@SuppressWarnings("serial")
public class BaseEntity<T extends BaseEntity<T>> extends IdEntity<T> {

    /**
     * 创建人
     */
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 最后修改人
     */
    @TableField(value = "last_updated_by", fill = FieldFill.INSERT_UPDATE)
    private Long lastUpdatedBy;

    /**
     * 最后修改时间
     */
    @TableField(value = "last_updated_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastUpdatedTime;
}
