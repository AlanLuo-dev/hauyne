package com.luoyx.hauyne.admin.util;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 统一线程池
 *
 * @author Alan.Luo
 */
public class ThreadPoolExecutorUtil {

    /**
     * 核心线程数
     */
    private static final int CORE_POLL_SIZE = 8;

    /**
     * 最大线程数
     */
    private static final int MAX_POLL_SIZE = 20;

    /**
     * 时间
     */
    private static final int KEEP_ALIVE_TIME = 600;

    private static final String THREAD_NAME_PREFIX = "av-erp-common-";

    private static ThreadPoolExecutor threadPoolExecutor = null;

    public static ThreadPoolExecutor getThreadPoolExecutor() {
        if (threadPoolExecutor == null || threadPoolExecutor.isShutdown()) {
            synchronized (ThreadPoolExecutorUtil.class) {
                if (threadPoolExecutor == null || threadPoolExecutor.isShutdown()) {
                    threadPoolExecutor = new ThreadPoolExecutor(CORE_POLL_SIZE, MAX_POLL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue(16), new ThreadFactory() {
                        private final AtomicInteger counter = new AtomicInteger();

                        @Override
                        public Thread newThread(Runnable r) {
                            String threadName = THREAD_NAME_PREFIX + this.counter.getAndIncrement();
                            return new Thread(r, threadName);
                        }
                    }, new ThreadPoolExecutor.CallerRunsPolicy());
                }
            }
        }
        return threadPoolExecutor;
    }
}
