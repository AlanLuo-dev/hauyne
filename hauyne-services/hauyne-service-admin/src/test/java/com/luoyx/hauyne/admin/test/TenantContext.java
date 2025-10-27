//package com.luoyx.hauyne.admin.test;
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
//@Slf4j
//public class TenantContext {
//
//    private static InheritableThreadLocal<String> tenantHolder = new InheritableThreadLocal<>();
//
//    public static void setTenantId(String tenantId){
//        tenantHolder.set(tenantId);
//    }
//
//    public static String getTenantId(){
//        return tenantHolder.get();
//    }
//
//    @Test
//    public void test1() throws InterruptedException {
//        ExecutorService ttlExecutorService = Executors.newFixedThreadPool(1);
//        TenantContext.setTenantId("mzt_" + 1);
//
//        ttlExecutorService.submit(() -> {
//           String tenantId = TenantContext.getTenantId();
//           log.info("当前线程：" + Thread.currentThread().getName() + "，租户ID：" + tenantId);
//        });
//
//        TimeUnit.SECONDS.sleep(2);
//
//        TenantContext.setTenantId("mzt_" + 2);
//        ttlExecutorService.submit(() -> {
//            String tenantId = TenantContext.getTenantId();
//            log.info("当前线程：" + Thread.currentThread().getName() + "，租户ID：" + tenantId);
//        });
//
//    }
//}
