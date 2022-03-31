package com.example.base.cache;

public class CacheData<T> {
    private String key;
    private T value;
    private Class<? extends CacheDataKind> cacheKindClazz;
    private int cacheType;
    private long timeout;

    /**
     *
     * @param key 键值
     * @param value 数据
     * @param cacheKindClazz 缓存类型
     * @param timeout 缓存时间 单位 ms
     */
    public CacheData(String key, T value, Class<? extends CacheDataKind> cacheKindClazz, long timeout) {
        this.key = key;
        this.value = value;
        this.cacheKindClazz = cacheKindClazz;
        this.cacheType = CacheDataKind.getKind(cacheKindClazz).getMixCacheType();
        this.timeout = timeout;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Class<? extends CacheDataKind> getCacheKindClazz() {
        return cacheKindClazz;
    }

    public void setCacheKindClazz(Class<? extends CacheDataKind> cacheKindClazz) {
        this.cacheKindClazz = cacheKindClazz;
    }

    public int getCacheType() {
        return cacheType;
    }

    public void setCacheType(int cacheType) {
        this.cacheType = cacheType;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
