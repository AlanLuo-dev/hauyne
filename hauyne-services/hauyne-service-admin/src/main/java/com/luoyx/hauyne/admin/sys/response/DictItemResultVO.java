package com.luoyx.hauyne.admin.sys.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 * 数据字典选项 维护功能的VO类
 * </p>
 *
 * @author LuoYingxiong
 * @since 2021-04-15
 */
@Getter
@Setter
@ToString
public class DictItemResultVO {

    /**
     * 主键id
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * 字典值
     */
    @Schema(description = "字典值编码")
    private String dictItemCode;

    /**
     * 字典值名称
     */
    @Schema(description = "字典值名称")
    private String dictItemName;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;

    /**
     * 启用状态
     */
    @Schema(description = "启用状态")
    private Boolean enabled;

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
