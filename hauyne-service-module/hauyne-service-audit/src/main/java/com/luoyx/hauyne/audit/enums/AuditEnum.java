package com.luoyx.hauyne.audit.enums;

import com.google.common.collect.ImmutableMap;
import com.luoyx.hauyne.admin.api.sys.audit.RoleAuditDTO;
import com.luoyx.hauyne.admin.api.sys.audit.RoleAuthorityAuditDTO;
import com.luoyx.hauyne.api.enumsupport.EnumSpec;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 审计类型枚举
 */
@Getter
@RequiredArgsConstructor
public enum AuditEnum implements EnumSpec<String, AuditEnum>{

    HYN_SYS_ROLE("hyn_sys_role", "角色日志", RoleAuditDTO.class),
    HYN_SYS_ROLE_AUTHORITY("hyn_sys_role_authority", "角色授权日志", RoleAuthorityAuditDTO.class),


    ;

    /**
     * 审计类型名称
     */
    private final String value;
    private final String label;

    /**
     * 审计类型对应的DTO类
     */
    private final Class<?> clazz;

    /**
     * 审计类型名称到DTO类的 不可变Map
     * key = 审计类型名称
     * value = 审计类型对应的DTO类
     */
    public static final ImmutableMap<String, Class<?>> TYPE_NAME_CLAZZ_MAP;

    /**
     * 静态代码块，初始化审计类型名称到DTO类的映射
     */
    static {
        ImmutableMap.Builder<String, Class<?>> builder = ImmutableMap.builder();
        for (AuditEnum item : AuditEnum.values()) {
            builder.put(item.getValue(), item.getClazz());
        }
        TYPE_NAME_CLAZZ_MAP = builder.build();
    }
}
