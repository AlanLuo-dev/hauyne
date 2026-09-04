package com.luoyx.hauyne.admin.api.sys.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.luoyx.hauyne.api.enumsupport.EnumSpec;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 权限类型枚举
 *
 * @author luoyingxiong
 */
@Getter
@RequiredArgsConstructor
public enum AuthorityTypeEnum implements EnumSpec<String, AuthorityTypeEnum> {

    MENU("menu", "菜单"),
    BUTTON("button", "按钮");

    @EnumValue
    private final String value;
    private final String label;
}
