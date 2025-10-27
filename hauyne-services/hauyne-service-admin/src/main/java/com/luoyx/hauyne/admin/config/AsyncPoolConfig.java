package com.luoyx.hauyne.admin.config;

import com.luoyx.hauyne.security.context.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步线程池配置
 *
 * @author 1564469545@qq.com
 */
@Slf4j
@Configuration
public class AsyncPoolConfig implements AsyncConfigurer {

    /**
     * 核心线程数（默认线程数）
     */
    private static final int CORE_POOL_SIZE = 5;
    /**
     * 最大线程数
     */
    private static final int MAX_POOL_SIZE = 10;
    /**
     * 允许线程空闲时间（单位：默认为秒）
     */
    private static final int KEEP_ALIVE_TIME = 180;
    /**
     * 缓冲队列大小
     */
    private static final int QUEUE_CAPACITY = 10;
    /**
     * 线程池名前缀
     */
    private static final String THREAD_NAME_PREFIX = "hauyne-thread-";

    public static final String TASK_EXECUTOR_NAME = "taskExecutor";

    @Override
    @Bean(TASK_EXECUTOR_NAME)
    public AsyncTaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数（默认线程数）
        executor.setCorePoolSize(CORE_POOL_SIZE);
        // 最大线程数
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        // 缓冲队列数
        executor.setQueueCapacity(QUEUE_CAPACITY);
        // 允许线程空闲时间（单位：默认为秒）
        executor.setKeepAliveSeconds(KEEP_ALIVE_TIME);
        // 线程池名前缀
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        // 设置是否等待计划任务在关闭时完成
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 设置此执行器应该阻止的最大秒数
        executor.setAwaitTerminationSeconds(60);
        // 增加 TaskDecorator 属性的配置
        executor.setTaskDecorator(new ContextDecorator());
        // 线程池对拒绝任务的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();

        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }


    /**
     * 任务装饰器
     */
    class ContextDecorator implements TaskDecorator {
        @Override
        public Runnable decorate(Runnable runnable) {
            RequestAttributes context = RequestContextHolder.currentRequestAttributes();
            Long userId = UserContextHolder.getUserId();
            return () -> {
                try {
                    if (log.isTraceEnabled()) {
                        log.trace(" -> 传递上下文");
                    }

                    // 传递上下文
                    RequestContextHolder.setRequestAttributes(context);
                    UserContextHolder.setUserId(userId);
                    runnable.run();
                } finally {
                    if (log.isTraceEnabled()) {
                        log.trace(" -> 重置上下文 开始");
                    }

                    // 重置上下文
                    RequestContextHolder.resetRequestAttributes();

                    // 清空当前线程的当前用户id
                    UserContextHolder.clear();

                    if (log.isTraceEnabled()) {
                        log.trace(" -> 重置上下文 结束");
                    }
                }
            };
        }
    }
}
