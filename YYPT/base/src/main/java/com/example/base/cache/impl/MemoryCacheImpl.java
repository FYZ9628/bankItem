package com.example.base.cache.impl;

import com.example.base.cache.CacheData;
import com.example.base.cache.CacheDataKind;
import com.example.base.cache.ICoreCache;

/**
 * JVM 内存缓存实现
 */
public class MemoryCacheImpl implements ICoreCache {
    private static final MemoryCacheManager MEMORY_CACHE_MANAGER = MemoryCacheManager.getInstance();

    @Override
    public void cache(CacheData cacheData) {
        MEMORY_CACHE_MANAGER.cache(cacheData);
    }

    @Override
    public <T> T get(String key, Class<? extends CacheDataKind> clazz) {
        return MEMORY_CACHE_MANAGER.cache(key, clazz);
    }

    @Override
    public void remove(String key, Class<? extends CacheDataKind> clazz) {
        MEMORY_CACHE_MANAGER.removeCache(key, clazz);
    }

    public void stop() {
        MEMORY_CACHE_MANAGER.stop();
    }
}
