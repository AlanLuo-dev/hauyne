package com.luoyx.hauyne.admin.validation;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.groups.Default;

/**
 * 人员类
 * <p>
 * 字段的验证 使用group分组
 */
@Data
public class Person {

    /**
     * 姓名（学生、老师都有的）
     */
    @NotBlank(groups = {Student.class, Teacher.class, Default.class})
    private String name;

    /**
     * 班级（学生特有）
     */
    @NotBlank(groups = {Student.class, Default.class})
    private String className;

    /**
     * 学号（学生特有）
     */
    @NotBlank(groups = {Student.class, Default.class})
    private String studentNo;
}