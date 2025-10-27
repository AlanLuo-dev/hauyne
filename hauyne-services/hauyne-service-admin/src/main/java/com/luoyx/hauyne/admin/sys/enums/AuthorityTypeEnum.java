package com.luoyx.hauyne.admin.sys.enums;

/**
 * 权限类型枚举
 *
 * @author luoyingxiong
 */
public enum AuthorityTypeEnum {

    MENU("menu", "菜单"),
    BUTTON("button", "按钮");

    private final String value;
    private final String text;

    AuthorityTypeEnum(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    /**
     * 数据校验
     *
     * @param value
     * @return
     */
    public static boolean validate(String value) {
        for (AuthorityTypeEnum authorityTypeEnum : AuthorityTypeEnum.values()) {
            if (authorityTypeEnum.value.equals(value)) {
                return true;
            }
        }

        return false;
    }
}
