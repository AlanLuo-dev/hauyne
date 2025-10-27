package com.luoyx.hauyne.admin.api.sys.audit;

import lombok.Data;
import org.javers.core.metamodel.annotation.Id;
import org.javers.core.metamodel.annotation.TypeName;

import java.util.List;

@TypeName("hyn_sys_role_authority")
@Data
public class RoleAuthorityAuditDTO {

    /**
     * 角色Id
     */
    @Id
    private Long roleId;

    /**
     * 角色拥有的权限
     */
    private List<String> authorityNames;
}
