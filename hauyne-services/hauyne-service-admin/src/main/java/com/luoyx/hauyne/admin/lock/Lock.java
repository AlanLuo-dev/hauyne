package com.luoyx.hauyne.admin.lock;

import java.util.function.Consumer;

/**
 * 锁接口
 *
 * @author Alan.Luo
 */
public interface Lock {

    /**
     * 获取锁
     * <p>
     * 尝试获取锁，未获取到立即返回 false
     *
     * @param lockKey the key
     * @return 是否获取锁
     */
    boolean tryLock(String lockKey);

    /**
     * 获取锁
     * <p>
     * 尝试获取锁，最多等待时间 {@code waitTime}
     *
     * @param lockKey   the key
     * @param waitTime  最多等待时间,单位秒
     * @param leaseTime 上锁后自动释放时间 单位秒
     * @return 是否获取锁
     */
    boolean tryLock(String lockKey, long waitTime, long leaseTime);

    /**
     * 释放锁
     *
     * @param lockKey the key
     */
    void unlock(String lockKey);

    /**
     * 获取锁
     *
     * @param lockKey       the key
     * @param waitTime      等待时间
     * @param leaseTime     释放时间
     * @param fair          是否公平锁
     * @param obj           入参对象
     * @param action        锁内动作
     * @param errorCallBack 锁内异常处理
     * @param <U>           入参类型
     * @return 是否获取锁
     */
    <U> boolean tryLock(String lockKey, long waitTime, long leaseTime, boolean fair, U obj,
                        Consumer<U> action,
                        Consumer<Exception> errorCallBack);
}
