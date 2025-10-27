package com.luoyx.hauyne.commons.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * VO 基类
 * <p>
 * 提供公共的字段：创建人、创建时间、最后修改人、最后修改时间
 *
 * @author 1564469545@qq.com
 */
@Getter
@Setter
@ToString
public class BaseVO implements Serializable {

    private static final long serialVersionUID = 6811026560797176980L;

    /**
     * 主键id
     */
    @Schema(description = "Id")
    private Long id;

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
