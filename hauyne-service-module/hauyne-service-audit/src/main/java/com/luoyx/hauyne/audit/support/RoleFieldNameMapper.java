package com.luoyx.hauyne.audit.support;

import com.google.common.collect.ImmutableMap;

/**
 * 角色 字段名 - 显示名称 映射
 *
 * @author Alan.Luo
 */
public class RoleFieldNameMapper {

    public static final ImmutableMap<String, String> ROLE_FIELD_NAME_MAP = ImmutableMap.of(
            "id", "主键ID",
            "roleCode", "角色编码",
            "roleName", "角色名称"
    );
}
