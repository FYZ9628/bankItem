package com.example.base.cache;

import com.example.base.cache.impl.MemoryCacheImpl;
import com.example.base.cache.impl.RedisCacheImpl;

public enum CacheType {
    /**
     * MEMORY
     */
    MEMORY(new MemoryCacheImpl(), CacheType.MEMORYCACHE),
    /**
     * REDIS
     */
    REDIS(new RedisCacheImpl(), CacheType.REDISCACHE)
    ;

    /**
     * Cache种类，以2次方表示  001,010,100.....(左移运算)
     */
    public static final int MEMORYCACHE = 1, REDISCACHE = MEMORYCACHE << 1;

    private ICoreCache coreCache;
    private int value;

    CacheType(ICoreCache coreCache, int value) {
        this.coreCache = coreCache;
        this.value = value;
    }

    public ICoreCache getCoreCache() {
        return coreCache;
    }

    public int getValue() {
        return value;
    }
}
