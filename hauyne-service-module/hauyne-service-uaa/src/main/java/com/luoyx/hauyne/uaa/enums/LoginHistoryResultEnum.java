package com.luoyx.hauyne.uaa.enums;

/**
 * 登录历史结果
 */
public enum LoginHistoryResultEnum {
    LOGIN_SUCCESS(1, "登录成功"),
    LOGIN_FAIL(2, "登录失败"),
    LOGOUT_SUCCESS(3, "注销成功"),
    LOGOUT_FAIL(4, "注销失败");

    private Integer value;
    private String label;

    LoginHistoryResultEnum(Integer value, String label) {
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
