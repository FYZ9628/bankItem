package com.example.base.param;

/**
 * 自定义缓存提供者
 * @param <T>
 */
public interface CustomerCacheProvider<T> {
    Class<? extends CustomerCacheProvider> DEFAULT_CACHE_PROVIDER = CustomerCacheProvider.class;

    long getCacheTime(T data);
}
