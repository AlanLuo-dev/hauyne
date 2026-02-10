package com.luoyx.hauyne.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.luoyx.hauyne.mybatisplus.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author 罗英雄
 * @since 2022-06-18
 */
@Getter
@Setter
@TableName("hyn_sys_authority")
@SuppressWarnings("serial")
public class Authority extends BaseEntity<Authority> {

    /**
     * 父权限id
     */
    private Long parentId;

    /**
     * 是否为叶子节点（1=是，0=否）
     */
    @TableField(value = "is_leaf")
    private Boolean leaf;

    /**
     * 权限树层级（1=第1层，2=第2层，以此类推）
     */
    private Integer level;

    /**
     * 权限类型（menu=菜单，operation=操作,可扩展）
     */
    private String authorityType;

    /**
     * 权限编码
     */
    private String authorityCode;

    /**
     * 权限名称
     */
    private String authorityName;

    /**
     * 图标CSS样式
     */
    private String icon;

    /**
     * 请求路径
     */
    private String path;

    /**
     * 排序(无符号)
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;

}
