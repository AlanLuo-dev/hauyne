package com.luoyx.hauyne.audit.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.metamodel.annotation.Entity;
import org.javers.core.metamodel.annotation.Id;
import org.javers.core.metamodel.annotation.ValueObject;
//import org.javers.core.shadow.Shadow;
import org.javers.repository.jql.QueryBuilder;
import org.javers.shadow.Shadow;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JaversShadowTest {

    @Test
    void shouldQueryForShadowsOfAnObject() {
        // given
        Javers javers = JaversBuilder.javers().build();

        Employee bob = new Employee("bob", 1000, new Address("London"));
        javers.commit("author", bob); // 初始提交Javers审计日志

        bob.setSalary(1200);
        bob.getPrimaryAddress().setCity("Paris");
        javers.commit("author", bob); // 修改工资、城市后，第二次提交Javers审计日志

        // 查询Bob的所有的投影
        List<Shadow<Employee>> shadows = javers.findShadows(
                QueryBuilder.byInstance(bob).build()
        );

        // 验证投影数量
        assertEquals(2, shadows.size());

        Employee bobNew = shadows.get(0).get(); // 最新的投影
        Employee bobOld = shadows.get(1).get(); // 老的投影

        assertEquals(1200, bobNew.getSalary());
        assertEquals(1000, bobOld.getSalary());

        assertEquals("Paris", bobNew.getPrimaryAddress().getCity());
        assertEquals("London", bobOld.getPrimaryAddress().getCity());

        assertEquals(2, shadows.get(0).getCommitMetadata().getId().getMajorId());
        assertEquals(1, shadows.get(1).getCommitMetadata().getId().getMajorId());
    }

    @Entity
    @Data
    @AllArgsConstructor
    static class Employee {
        @Id
        private String name;
        private int salary;
        private Address primaryAddress;
    }

    @ValueObject // 何意？
    @Data
    @AllArgsConstructor
    static class Address {
        private String city;
    }
}

