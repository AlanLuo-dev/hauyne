package com.luoyx.hauyne.admin.sys.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 角色编码唯一性校验 查询条件
 *
 * @author luo_yingxiong@163.com
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoleCodeUniqueCheckQuery {

    /**
     * 要排除的角色Id（编辑角色的场景）
     */
    private Long excludeRoleId;

    /**
     * 角色编码
     */
    private String roleCode;
}
