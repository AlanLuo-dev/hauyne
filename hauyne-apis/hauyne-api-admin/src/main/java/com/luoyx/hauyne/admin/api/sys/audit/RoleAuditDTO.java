package com.luoyx.hauyne.admin.api.sys.audit;

import lombok.Data;
import org.javers.core.metamodel.annotation.Id;
import org.javers.core.metamodel.annotation.TypeName;

/**
 * 角色审计日志 数据传输对象
 *
 * @author Alan.Luo
 */
@TypeName("hyn_sys_role")
@Data
public class RoleAuditDTO {

    /**
     * 角色Id（主键ID）
     */
    @Id
    private Long id;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;
}
