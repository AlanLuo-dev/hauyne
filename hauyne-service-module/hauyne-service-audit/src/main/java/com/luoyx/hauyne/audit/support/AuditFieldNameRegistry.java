package com.luoyx.hauyne.audit.support;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;
import java.util.Map;

public class AuditFieldNameRegistry {
    private static final Map<String, Map<String, String>> FIELD_NAME_MAP;

    static {
        ImmutableMap.Builder<String, Map<String, String>> builder = ImmutableMap.builder();

        builder.put("hyn_sys_role", RoleFieldNameMapper.ROLE_FIELD_NAME_MAP);
        builder.put("hyn_sys_role_authority", RoleAuthorityFieldNameMapper.ROLE_AUTHORITY_FIELD_NAME_MAP);
        // 可添加更多类型

        // 构建不可变映射
        FIELD_NAME_MAP = builder.build();
    }

    public static String resolve(String typeName, String field) {
        return FIELD_NAME_MAP.getOrDefault(typeName, Collections.emptyMap())
                .getOrDefault(field, field);
    }
}

