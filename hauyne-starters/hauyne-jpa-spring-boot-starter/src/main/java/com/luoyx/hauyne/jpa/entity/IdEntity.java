package com.luoyx.hauyne.jpa.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Alan.Luo
 * @since 2023/4/27 23:22
 * @MappedSuperclass 是一个 JPA 的注解，用于标识一个类为一个映射的超类，即该类不会被映射到数据库中，但其属性会被子类映射到数据库表中。
 * <p>
 * 在使用 JPA 的时候，我们通常需要定义一个实体基类，其中包含一些共同的属性和方法，然后其他实体类通过继承该基类来实现属性和方法的继承。
 * 此时，我们需要在基类上标注 @MappedSuperclass 注解，以表明该类为一个超类，不需要被映射到数据库中。
 * <p>
 * 在使用 @MappedSuperclass 注解后，基类中的属性就可以被子类继承，并且可以使用基类中定义的 @Column、@Id、@GeneratedValue 等注解来设置子类的属性。
 * <p>
 * 因此，@MappedSuperclass 在 JPA 中是非常重要的，如果忘记添加该注解，可能会导致实体类无法正常继承超类的属性。
 */
@Data
@MappedSuperclass
public class IdEntity implements Serializable {

    private static final long serialVersionUID = 3090438747302730319L;

    /**
     * 主键，无符号自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
