package com.luoyx.hauyne.admin.sys.query;

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
}
