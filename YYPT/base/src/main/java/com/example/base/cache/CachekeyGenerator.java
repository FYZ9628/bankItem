package com.example.base.cache;

@SPI
public interface CachekeyGenerator {
    String getKey(String key, Class<? extends CacheDataKind> clazz);
}
