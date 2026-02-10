package com.luoyx.hauyne.admin.lock;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * 基于redisson的分布式锁实现
 *
 * @author Alan.Luo
 */
@Slf4j
public class RedissonLock implements Lock {

    @Resource
    private RedissonClient redissonClient;

    @Override
    public boolean tryLock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        return lock.tryLock();
    }

    @Override
    public boolean tryLock(String lockKey, long waitTime, long leaseTime) {
        try {
            RLock lock = redissonClient.getLock(lockKey);
            return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            String msg = String.format("尝试加锁出现异常：%s", e.getMessage());
            throw new RuntimeException(msg);
        }
    }

    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        if (lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    @Override
    public <U> boolean tryLock(String lockKey, long waitTime, long leaseTime, boolean fair, U obj,
                               Consumer<U> action,
                               Consumer<Exception> errorCallBack) {
        RLock lock;
        if (fair) {
            lock = redissonClient.getFairLock(lockKey);
        } else {
            lock = redissonClient.getLock(lockKey);
        }
        try {
            boolean lockResult = lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
            if (!lockResult) {
                return false;
            }
            action.accept(obj);
        } catch (Exception exception) {
            errorCallBack.accept(exception);
        } finally {
            if (lock.isLocked()) {
                lock.unlock();
            }
        }
        return true;
    }
}
