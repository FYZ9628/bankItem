package com.example.base.cache.impl;

import com.example.base.cache.CacheDataKind;
import com.example.base.cache.CachekeyGenerator;

public class RedisKeyGenerator implements CachekeyGenerator {
    @Override
    public String getKey(String key, Class<? extends CacheDataKind> clazz) {
        return clazz.getSimpleName() + "-" + key;
    }
}
