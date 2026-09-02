package com.luoyx.hauyne.admin.sys.response;

import com.luoyx.hauyne.admin.api.sys.enums.YesNoEnum;
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

    @Schema(description = "主键id")
    private Long id;

    @Schema(description = "角色编码")
    private String roleCode;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "是否系统内置角色（false=否，true=是）")
    private YesNoEnum builtin;

    @Schema(description = "创建人姓名")
    private String createdBy;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    @Schema(description = "最后修改人的真实姓名")
    private String lastUpdatedBy;

    @Schema(description = "最后修改时间")
    private LocalDateTime lastUpdatedTime;
}
