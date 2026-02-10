package com.luoyx.hauyne.admin.sys.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 角色 列表数据VO 类
 *
 * @author luoyingxiong
 */
@Getter
@Setter
@ToString
public class RolePageResultVO {

    /**
     * 主键id
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    private String roleCode;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String roleName;

    /**
     * 创建人的真实姓名
     */
    @Schema(description = "创建人姓名")
    private String createdBy;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    /**
     * 最后修改人的真实姓名
     */
    @Schema(description = "最后修改人的真实姓名")
    private String lastUpdatedBy;

    /**
     * 最后修改时间
     */
    @Schema(description = "最后修改时间")
    private LocalDateTime lastUpdatedTime;
}
