package com.example.base.cache.impl;

import com.example.base.cache.CacheData;
import com.example.base.cache.CacheDataKind;
import com.example.base.cache.ICoreCache;
import org.springframework.stereotype.Component;

@Component
public class MixCoreCacheImpl implements ICoreCache {
    @Override
    public void cache(CacheData cacheData) {

    }

    @Override
    public <T> T get(String key, Class<? extends CacheDataKind> clazz) {
        return null;
    }

    @Override
    public void remove(String key, Class<? extends CacheDataKind> clazz) {

    }
}
