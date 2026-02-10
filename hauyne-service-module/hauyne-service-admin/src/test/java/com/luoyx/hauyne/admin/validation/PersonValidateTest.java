//package com.luoyx.hauyne.admin.validation;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.Validation;
//import jakarta.validation.Validator;
//import jakarta.validation.ValidatorFactory;
//import java.util.Set;
//
///**
// * 测试类
// */
//public class PersonValidateTest {
//
//    private Person person;
//    private Validator validator;
//
//    @Before
//    public void init() {
//        this.person = new Person();
//
//        // 初始化validator
//        final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
//        this.validator = validatorFactory.getValidator();
//    }
//
//    @Test
//    public void testStudent() {
//        this.person.setName("dylan");
//        this.person.setClassName("andClass");
//        this.person.setStudentNo("11250401128");
//
//        final Set<ConstraintViolation<Person>> validate = this.validator.validate(this.person, Student.class);
//        Assert.assertEquals(0, validate.size());
//    }
//
//    @Test
//    public void testTeacher() {
//        this.person.setName("kevin");
//
//        final Set<ConstraintViolation<Person>> validate = this.validator.validate(this.person, Teacher.class);
//        Assert.assertEquals(0, validate.size());
//    }
//
//    @Test
//    public void testEmpty() {
//        final Set<ConstraintViolation<Person>> validateA = this.validator.validate(this.person, Student.class);
//        Assert.assertEquals(3, validateA.size());
//
//        final Set<ConstraintViolation<Person>> validateB = this.validator.validate(this.person, Teacher.class);
//        Assert.assertEquals(1, validateB.size());
//    }
//
//    /**
//     * 在分组信息属性groups没有值的时候，它具有默认值Default.class。在此前没有加入分组信息，使用validate()进行校验时，程序都会自动地添加一个默认的分组Default.class。
//     */
//    @Test
//    public void testNoGroups() {
//        final Set<ConstraintViolation<Person>> validate = this.validator.validate(this.person);
//        Assert.assertEquals(3, validate.size());
//    }
//
//
//}
