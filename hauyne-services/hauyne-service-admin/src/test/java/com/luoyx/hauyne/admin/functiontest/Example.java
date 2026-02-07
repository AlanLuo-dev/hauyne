package com.luoyx.hauyne.admin.functiontest;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class Example {
    public static void main(String[] args) {
        Student student = new Student();
        student.setCreatedBy(1L);
        student.setLastUpdatedBy(2L);

        List<Student> studentList = new ArrayList<>();
        studentList.add(student);

        Map<Function<Student, Long>, BiConsumer<Student, String>> fieldConverterMap = new HashMap<>();
        fieldConverterMap.put(Student::getCreatedBy, Student::setCreatedByFullName);
        fieldConverterMap.put(Student::getLastUpdatedBy, Student::setLastModifiedByFullName);

        UserNameConverter.convertLastModifiedBy(studentList, fieldConverterMap);

        BiConsumer<Student, String> biConsumer = Student::setLastModifiedByFullName;
        Class<? extends BiConsumer> aClass = biConsumer.getClass();

        System.out.println(student.toString()); // 输出转换后的用户名
    }
}

