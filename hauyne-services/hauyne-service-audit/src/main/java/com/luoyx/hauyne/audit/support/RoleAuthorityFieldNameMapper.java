package com.luoyx.hauyne.audit.support;

import com.google.common.collect.ImmutableMap;

public class RoleAuthorityFieldNameMapper {

    public static final ImmutableMap<String, String> ROLE_AUTHORITY_FIELD_NAME_MAP = ImmutableMap.of(
            "roleId", "角色id",
            "authorityNames", "权限名称"
    );
}
