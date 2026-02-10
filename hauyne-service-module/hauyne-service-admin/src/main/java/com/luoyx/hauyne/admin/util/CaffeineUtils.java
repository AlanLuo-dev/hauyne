package com.luoyx.hauyne.admin.util;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Caffeine缓存工具类
 *
 * @Author gblfy
 * @Date 2022-03-15 14:58
 **/
@Component
public class CaffeineUtils {

    @Autowired
    Cache<String, Object> caffeineCache;

    /**
     * 添加或更新缓存
     *
     * @param key
     * @param value
     */
    public void putAndUpdateCache(String key, Object value) {
        caffeineCache.put(key, value);
    }


    /**
     * 获取对象缓存
     *
     * @param key
     * @return
     */
    public <T> T getObjCacheByKey(String key, Class<T> t) {
        caffeineCache.getIfPresent(key);
        return (T) caffeineCache.asMap().get(key);
    }

    /**
     * 根据key删除缓存
     *
     * @param key
     */
    public void removeCacheByKey(String key) {
        // 从缓存中删除
        caffeineCache.asMap().remove(key);
    }
}