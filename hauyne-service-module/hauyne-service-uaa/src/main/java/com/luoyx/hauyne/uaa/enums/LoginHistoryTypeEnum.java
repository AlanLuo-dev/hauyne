package com.luoyx.hauyne.uaa.enums;

/**
 * 登录历史类型
 */
public enum LoginHistoryTypeEnum {

    LOGIN(1, "登录"),
    LOGOUT(0, "注销");

    private Integer value;
    private String label;

    LoginHistoryTypeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
