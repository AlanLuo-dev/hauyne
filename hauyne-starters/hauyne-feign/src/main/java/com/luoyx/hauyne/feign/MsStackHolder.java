package com.luoyx.hauyne.feign;


import com.luoyx.hauyne.api.FeignCallRecord;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * ThreadLocal 用于存储 MsStack 对象
 *
 * @author 罗英雄
 */
public class MsStackHolder {



    private static ThreadLocal<FeignCallRecord> threadLocal = new ThreadLocal<>();

    public static void set(FeignCallRecord feignCallRecord) {
        System.out.println(" 开始设置 MsStack 当前线程：" + Thread.currentThread().getName());
        threadLocal.set(feignCallRecord);
    }

    public static FeignCallRecord get() {
        System.out.println("开始获取 当前线程：" + Thread.currentThread().getName());
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }


//    private static final ThreadLocal<Deque<FeignCallRecord>> callStack = ThreadLocal.withInitial(ArrayDeque::new);
//
//    public static void pushCallRecord(FeignCallRecord record) {
//        callStack.get().addLast(record);
//    }
//
//    public static List<FeignCallRecord> getCallRecords() {
//        return new ArrayList<>(callStack.get());
//    }
//
//    public static void clear() {
//        callStack.remove();
//    }
}
