package com.luoyx.hauyne.audit.test;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ImmutableTest {
    @Test
    public void testJDKImmutable() {
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        System.out.println(list);

        List<String> unmodifiableList = Collections.unmodifiableList(list);


        list.add("baby");
        System.out.println("list add a item after list:" + list);
        System.out.println("list add a item after unmodifiableList:" + unmodifiableList);

    }

    @Test
    public void testGuavaImmutableMap() {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        builder.put("a", "a");
        builder.put("a", "189");
        ImmutableMap<String, String> build = builder.build();
        System.out.println(build);
    }
}
