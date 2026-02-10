package com.luoyx.hauyne.admin.sys.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 数据字典类型 VO类 维护功能
 *
 * @author luoyingxiong
 */
@Getter
@Setter
@ToString(callSuper = true)
public class DictTypeDetailVO {

    /**
     * Id
     */
    @Schema(description = "id")
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
     * 创建人id
     */
    @JsonIgnore
    private Long createdBy;

    /**
     * 创建人的真实姓名
     */
    @Schema(description = "创建人的真实姓名")
    private String createdByFullName;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    /**
     * 最后修改人id
     */
    @JsonIgnore
    private Long lastUpdatedBy;

    /**
     * 最后修改人的真实姓名
     */
    @Schema(description = "最后修改人的真实姓名")
    private String lastModifiedByFullName;

    /**
     * 最后修改时间
     */
    @Schema(description = "最后修改时间")
    private LocalDateTime lastUpdatedTime;
}
