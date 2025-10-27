package com.luoyx.hauyne.admin.sys.converter;

import com.luoyx.hauyne.admin.api.sys.audit.RoleAuditDTO;
import com.luoyx.hauyne.admin.sys.entity.Role;
import com.luoyx.hauyne.admin.sys.request.RoleCreateDTO;
import com.luoyx.hauyne.admin.sys.request.RoleUpdateDTO;
import com.luoyx.hauyne.admin.sys.response.RoleVO;
import org.mapstruct.Mapper;

/**
 * 角色 Bean转换接口
 *
 * @author luoyingxiong
 */
@Mapper(componentModel = "spring")
public interface RoleConverter {

    /**
     * 新增参数转换为实体
     *
     * @param roleCreateDTO
     * @return
     */
    Role toRole(RoleCreateDTO roleCreateDTO);

    /**
     * 修改参数转换为实体
     *
     * @param roleUpdateDTO
     * @return
     */
    Role toRole(RoleUpdateDTO roleUpdateDTO);

    RoleVO toRoleVO(Role role);

    RoleAuditDTO toRoleAuditDTO(Role role);
}
