package com.example.base.cache.impl;

import com.example.base.cache.CacheData;
import com.example.base.cache.CacheDataKind;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * JVM 内存缓存实现
 */
public class MemoryCacheManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryCacheManager.class);
    private static final MemoryCacheManager INSTANCE_MANAGER = new MemoryCacheManager();
    private ConcurrentHashMap<Class<? extends CacheDataKind>, Cache<String, Object>> cache = new ConcurrentHashMap<>();

    private MemoryCacheManager() {
    }

    public static MemoryCacheManager getInstance() {
        return INSTANCE_MANAGER;
    }

    private Cache<String, Object> getCache(CacheData cacheData) {
        return cacheData.getTimeout() > 0 ?
                Caffeine.newBuilder()
                        .initialCapacity(1024)
                        .maximumSize(9216)
                        //大于0的时候才会被置为超时任务执行
                        .expireAfterAccess(cacheData.getTimeout(), TimeUnit.SECONDS)
                        .removalListener(new MemoryCacheRemovalListener())
                        .build():
                Caffeine.newBuilder()
                        .initialCapacity(1024)
                        .maximumSize(9216)
                        .removalListener(new MemoryCacheRemovalListener())
                        .build();
    }

    /**
     * 缓存当前对象
     *
     * @param cacheData
     */
    public void cache(CacheData cacheData) {
        Class<? extends CacheDataKind> cacheKindClazz = cacheData.getCacheKindClazz();
        Cache<String, Object> cacheOfType = cache.computeIfAbsent(cacheKindClazz, k -> getCache(cacheData));
        cacheOfType.put(cacheData.getKey(), cacheData.getValue());
        if (LOGGER.isDebugEnabled()) {
            Object value = cacheData.getValue();
            if (value instanceof Collection && value != null) {
                int maxIndex = ((Collection)value).size();
                if (maxIndex > 5) {
                    maxIndex = 5;
                }
                LOGGER.debug("cache data in Memory {} for key {} with timeout {} ", ((Collection)value).stream().limit(maxIndex).collect(Collectors.toList()), cacheData.getKey(), cacheData.getTimeout());
            } else if (value instanceof Map && value != null) {
                int maxindex = ((Map)value).size();
                if (maxindex > 5) {
                    maxindex = 5;
                }
                LOGGER.debug("cache data in Memory {} for key {} with timeout {} ", value, cacheData.getKey(), cacheData.getTimeout());
            }
        }
    }

    @Deprecated
    public void removeCache(CacheData cacheData) {
        Class<? extends CacheDataKind> cacheKindClazz = cacheData.getCacheKindClazz();
        removeCache(cacheData.getKey(), cacheKindClazz);
    }

    public void removeCache(String key, Class<? extends CacheDataKind> cacheKindClazz) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("removeCache {} ", cacheKindClazz.getName());
        }
        Cache<String, Object> cacheOfType = cache.get(cacheKindClazz);
        if (cacheOfType != null) {
            cacheOfType.invalidate(key);
        }
    }

    public <T> T cache(String key, Class<? extends CacheDataKind> cacheKindClazz) {
        Cache<String, Object> cacheOfType = cache.get(cacheKindClazz);
        if (cacheOfType == null) {
            return null;
        }
        return (T)cacheOfType.getIfPresent(key);
    }

    public void stop() {

    }

    static final class MemoryCacheRemovalListener implements RemovalListener<String, Object> {
        @Override
        public void onRemoval(@Nullable String key, @Nullable Object value, RemovalCause cause) {
            if (LOGGER.isDebugEnabled()) {
            }
            LOGGER.debug("removeCache {} ", key);
        }
    }
}

