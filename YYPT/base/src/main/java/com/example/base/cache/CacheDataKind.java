package com.example.base.cache;

import java.util.concurrent.ConcurrentHashMap;

public abstract class CacheDataKind {
    private static final ConcurrentHashMap<Class<? extends CacheDataKind>, CacheDataKind> CACHE_DATA_KIND = new ConcurrentHashMap<>();

    public static final CacheDataKind getKind(Class<? extends CacheDataKind> clazz) {
        return CACHE_DATA_KIND.computeIfAbsent(clazz, (c) ->{
            try {
                CacheDataKind kind = clazz.newInstance();
                return kind;
            } catch (Exception e) {
            }
            return new InvalidateKind();
        });
    }

    public int getMixCacheType() {
        CacheType[] types = getCacheTypes();
        int mixTypes = 0;
        for (CacheType type : types) {
            mixTypes = mixTypes | type.getValue();
        }
        return mixTypes;
    }

    public abstract CacheType[] getCacheTypes();
}

class InvalidateKind extends CacheDataKind {

    @Override
    public CacheType[] getCacheTypes() {
        return new CacheType[]{};
    }
}
