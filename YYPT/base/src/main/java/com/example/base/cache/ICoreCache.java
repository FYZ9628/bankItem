package com.example.base.cache;

public interface ICoreCache {
    /**
     * 缓存数据
     * @param cacheData
     */
    void cache(CacheData<?> cacheData);

    /**
     * 获取缓存数据
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T get(String key, Class<? extends CacheDataKind> clazz);

    /**
     * 删除缓存
     * @param key
     * @param clazz
     */
    void remove(String key, Class<? extends CacheDataKind> clazz);
}
