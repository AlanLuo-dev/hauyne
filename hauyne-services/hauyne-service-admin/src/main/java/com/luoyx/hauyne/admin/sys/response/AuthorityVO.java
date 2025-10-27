package com.luoyx.hauyne.admin.sys.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 权限列表数据 VO 类
 *
 * @author 1564469545@qq.com
 */
@Getter
@Setter
@ToString
@SuppressWarnings("serial")
public class AuthorityVO {

    /**
     * 主键id
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * 父权限id (不序列化)
     */
    @JsonIgnore
    private Long parentId;

    /**
     * 是否为叶子节点（是，否）
     */
    private Boolean leaf;

    /**
     * 权限树层级（1=第1层，2=第2层，以此类推）
     */
    private Integer level;

    /**
     * 权限类型（menu=菜单，button=按钮）
     */
    @Schema(description = "权限类型（menu=菜单，button=按钮）")
    private String authorityType;

    /**
     * 权限编码
     */
    @Schema(description = "权限编码")
    private String authorityCode;

    /**
     * 权限名称
     */
    @Schema(description = "权限名称")
    private String authorityName;

    /**
     * 图标CSS样式
     */
    @Schema(description = "图标")
    private String icon;

    /**
     * 请求路径
     */
    @Schema(description = "请求路径")
    private String path;

    /**
     * 排序(无符号)
     */
    @Schema(description = "排序(无符号)")
    private Integer sort;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 创建人姓名
     */
    @Schema(description = "创建人姓名")
    private String createdBy;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    /**
     * 最后修改人的姓名
     */
    @Schema(description = "最后修改人的姓名")
    private String lastUpdatedBy;

    /**
     * 最后修改时间
     */
    @Schema(description = "最后修改时间")
    private LocalDateTime lastUpdatedTime;
}
