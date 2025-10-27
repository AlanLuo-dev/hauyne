package com.luoyx.hauyne.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Id 基类
 *
 * @param <T>
 * @author Alan.Luo
 */
@Getter
@Setter
public class IdEntity<T extends IdEntity<T>> extends Model<T> implements Serializable {

    private static final long serialVersionUID = -18169353206552693L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
