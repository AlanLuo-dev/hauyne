package com.luoyx.hauyne.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class UserNameConverter {

    public static <T> void convertLastModifiedBy(T pojo,
                                                 Function<T, Long> getUserIdFn,
                                                 BiConsumer<T, String> setUserNameFn,
                                                 Map<Long, String> userCache) {
        long userId = getUserIdFn.apply(pojo);
        String userName = userCache.get(userId);
        setUserNameFn.accept(pojo, userName);
    }

    public static <T> void convertLastModifiedBy(List<T> pojoList,
                                                 Map<Function<T, Long>, BiConsumer<T, String>> getSetFuncMap) {
        Map<Long, String> userCache = new HashMap<>();
        userCache.put(1L, "张三");
        userCache.put(2L, "李四");

        pojoList.forEach(item -> {
            getSetFuncMap.entrySet().stream()
                    .forEach(func -> {
                        long userId = func.getKey().apply(item);
                        String userName = userCache.get(userId);
                        func.getValue().accept(item, userName);
                    });
        });


    }
}
