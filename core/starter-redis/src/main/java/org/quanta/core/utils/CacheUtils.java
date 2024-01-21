package org.quanta.core.utils;

import org.quanta.base.utils.SpringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

/**
 * Description: 缓存工具
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/9/18
 */
public class CacheUtils {
    private volatile static CacheManager cacheManager;

    /**
     * 双检锁单例模式
     */
    private static CacheManager getCacheManager() {
        if (cacheManager == null) {
            synchronized (CacheManager.class) {
                if (cacheManager == null) {
                    cacheManager = SpringUtils.getBean(CacheManager.class);
                }
            }
        }
        return cacheManager;
    }

    /**
     * 获取缓存对象
     *
     * @param name 缓存名
     * @return 缓存对象
     */
    public static Cache getCache(String name) {
        return getCacheManager().getCache(name);
    }

    /**
     * 清除缓存
     *
     * @param cacheName 缓存名
     * @param key       缓存键值
     */
    public static void evict(String cacheName, String key) {
        getCache(cacheName).evict(key);
    }
}
