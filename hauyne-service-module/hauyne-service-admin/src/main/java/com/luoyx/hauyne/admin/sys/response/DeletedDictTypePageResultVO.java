package com.luoyx.hauyne.admin.sys.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 已删除的数据字典类型 VO类
 *
 * @author luoyingxiong
 */
@Getter
@Setter
@ToString
public class DeletedDictTypePageResultVO {

    /**
     * 主键id
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * 字典类型编码
     */
    @Schema(description = "字典类型编码")
    private String dictTypeCode;

    /**
     * 字典类型名称
     */
    @Schema(description = "字典类型名称")
    private String dictTypeName;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String description;

    /**
     * 是否已启用
     */
    @Schema(description = "是否已启用【true=已启用，false=已禁用】")
    private Boolean enabled;

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
     * 最后修改人姓名
     */
    @Schema(description = "最后修改人姓名")
    private String lastUpdatedBy;

    /**
     * 最后修改时间
     */
    @Schema(description = "最后修改时间")
    private LocalDateTime lastUpdatedTime;

    /**
     * 删除者姓名
     */
    @Schema(description = "删除者姓名")
    private String deletedBy;

    /**
     * 删除时间
     */
    @Schema(description = "删除时间")
    private LocalDateTime deletedTime;
}
