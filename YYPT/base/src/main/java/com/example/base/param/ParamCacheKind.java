package com.example.base.param;

import com.example.base.cache.CacheDataKind;
import com.example.base.cache.CacheType;

public class ParamCacheKind extends CacheDataKind {
    @Override
    public CacheType[] getCacheTypes() {
        return new CacheType[]{CacheType.MEMORY};
    }
}
