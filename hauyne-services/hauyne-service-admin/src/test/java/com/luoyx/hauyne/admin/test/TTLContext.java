package com.luoyx.hauyne.admin.test;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import org.junit.jupiter.api.Test;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TTLContext {

    private static TransmittableThreadLocal<String> tenantHolder = new TransmittableThreadLocal<>();

    public static void setUserId(String userId){
        tenantHolder.set(userId);
    }

    public static String getUserId(){
        return tenantHolder.get();
    }

    @Test
    public void test1() throws InterruptedException {

        // 创建一个线程池，线程数量固定为1
        ExecutorService ttlExecutorService = TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(1));

        // 设置当前线程的租户ID
        TTLContext.setUserId("mzt_" + 1);
        ttlExecutorService.submit(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getName() + "，租户ID：" + TTLContext.getUserId());
        });

        TimeUnit.SECONDS.sleep(2);

        // 设置当前线程的租户ID
        TTLContext.setUserId("mzt_" + 2);
        ttlExecutorService.submit(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getName() + "，租户ID：" + TTLContext.getUserId());
        });

        Thread.sleep(2000L);
    }
}
