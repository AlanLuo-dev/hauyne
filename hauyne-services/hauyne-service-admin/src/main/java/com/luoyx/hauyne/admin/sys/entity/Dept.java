package com.luoyx.hauyne.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.luoyx.hauyne.mybatisplus.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author 1564469545@qq.com
 * @since 2024-05-11
 */
@Getter
@Setter
@ToString
@TableName("hyn_sys_dept")
public class Dept extends BaseEntity<Dept> {

    /**
     * 父级部门id
     */
    private Long parentId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门负责人id
     */
    private Long leaderId;

    /**
     * 部门层级
     */
    private Integer level;

    /**
     * 备注
     */
    private String remark;
}
