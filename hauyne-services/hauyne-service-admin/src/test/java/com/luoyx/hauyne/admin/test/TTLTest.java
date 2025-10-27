//package com.luoyx.hauyne.admin.test;
//
//import com.alibaba.ttl.TransmittableThreadLocal;
//import com.alibaba.ttl.TtlRunnable;
//import org.junit.Test;
//
//import java.util.UUID;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class TTLTest {
//
//    @Test
//    public void givenInheritableThreadLocal_whenChangeTheTransactionIdAfterSubmissionToThreadPool_thenNewValueWillNotBeAvailableInParallelThread() {
//
//        /*
//            创建一个线程池，线程数量固定为1
//            不会立刻创建线程，只是创建了一个具有线程池功能的 ExecutorService 实例。
//         */
//        ExecutorService executorService = Executors.newFixedThreadPool(1);
//
//        // 创建一个InheritableThreadLocal对象，用于存储事务ID
//        InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
//
//        String firstTransactionIDValue = UUID.randomUUID().toString();
//        inheritableThreadLocal.set(firstTransactionIDValue);
//
//        Runnable task = () -> {
//            System.out.println("原始值：" + firstTransactionIDValue + "  InheritableThreadLocal中获取的值: " + inheritableThreadLocal.get());
//            System.out.println("——————————————————————————————————————————————————");
//        };
//
//        // 运行第一个Runnable异步任务
//        executorService.submit(task);
//
//        String secondTransactionIDValue = UUID.randomUUID().toString();
//        inheritableThreadLocal.set(secondTransactionIDValue);
//        Runnable task2 = () -> {
//            System.out.println("原始值：" + secondTransactionIDValue + "  InheritableThreadLocal中获取的值: " + inheritableThreadLocal.get());
//        };
//
//        // 运行第二个Runnable异步任务
//        executorService.submit(task2);
//
//        // 关闭线程池
//        executorService.shutdown();
//    }
//
//
//
//    @Test
//    public void givenTransmittableThreadLocal_whenChangeTheTransactionIdAfterSubmissionToThreadPool_thenNewValueWillBeAvailableInParallelThread() {
//
//        String firstTransactionIDValue = UUID.randomUUID().toString();
//        String secondTransactionIDValue = UUID.randomUUID().toString();
//
//        TransmittableThreadLocal<String> transmittableThreadLocal = new TransmittableThreadLocal<>();
//        transmittableThreadLocal.set(firstTransactionIDValue);
//
//        Runnable task = () -> {
//            System.out.println("原始值: " + firstTransactionIDValue + "  transmittableThreadLocal中的值： " + transmittableThreadLocal.get());
//        };
//        Runnable task2 = () -> {
//            System.out.println("原始值: " + secondTransactionIDValue + "  transmittableThreadLocal中的值： " + transmittableThreadLocal.get());
//        };
//
//        ExecutorService executorService = Executors.newFixedThreadPool(1);
//        executorService.submit(TtlRunnable.get(task));
//
//        transmittableThreadLocal.set(secondTransactionIDValue);
//        executorService.submit(TtlRunnable.get(task2));
//
//        executorService.shutdown();
//    }
//}
