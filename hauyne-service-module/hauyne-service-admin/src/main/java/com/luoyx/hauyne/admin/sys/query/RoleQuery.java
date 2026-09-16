package com.luoyx.hauyne.admin.sys.query;

import com.luoyx.hauyne.admin.api.sys.enums.YesNoEnum;
import com.luoyx.hauyne.mybatisplus.query.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 角色查询参数
 *
 * @author luoyingxiong
 */
@Getter
@Setter
@ToString
public class RoleQuery extends PageQuery {

    @Schema(description = "角色编码")
    private String roleCode;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "是否内置")
    private YesNoEnum builtIn;
}
