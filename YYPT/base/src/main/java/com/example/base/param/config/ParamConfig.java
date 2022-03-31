package com.example.base.param.config;

import com.example.base.cache.CacheDataKind;
import com.example.base.param.CustomerCacheProvider;
import com.example.base.param.ParamBean;
import com.example.base.param.ParamBeanWrap;
import com.example.base.param.ParamCacheKind;
import com.example.base.param.annotation.CoreBankParam;
import com.example.base.param.provider.ParamProviderService;
import org.apache.ibatis.javassist.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class ParamConfig {
    private static final Map<Class<?>, Class<? extends ParamBeanWrap>> MAPS = new HashMap<>();
    private static final String PARAM_CACHE_CENTER_PREFIX = "ParamCacheCenter:";
    private Class<? extends CustomerCacheProvider> customCacheProvider;
    private boolean local;
    private boolean lazy;
    private boolean loadAll;
    private boolean cache;
    private Class<?> paramType;
    private String paramName;
    private String cacheKey;
    private ParamProviderService providerService;
    private Class<? extends CacheDataKind> clazzOfDataKind;
    private long validTime;
    private final AtomicBoolean threadRunning = new AtomicBoolean(false);
    private boolean sensitiveKey;

    public ParamConfig(CoreBankParam param) throws NotFoundException, CannotCompileException {
        this.lazy = param.lazy();
        this.loadAll = param.loadAll();
        this.local = param.local();
        this.paramName = param.paramName();
        this.cache = param.cache();
        this.cacheKey = PARAM_CACHE_CENTER_PREFIX + paramName;
        this.clazzOfDataKind = createAndLoadClass();
        this.validTime = param.validTime();
        this.customCacheProvider = param.customCacheProvider();
        this.sensitiveKey = param.sensitiveKey();
    }

    public Class<? extends CustomerCacheProvider> getCustomCacheProvider() {
        return customCacheProvider;
    }

    public void setCustomCacheProvider(Class<? extends CustomerCacheProvider> customCacheProvider) {
        this.customCacheProvider = customCacheProvider;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public boolean isLazy() {
        return lazy;
    }

    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }

    public boolean isLoadAll() {
        return loadAll;
    }

    public void setLoadAll(boolean loadAll) {
        this.loadAll = loadAll;
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public Class<?> getParamType() {
        return paramType;
    }

    public void setParamType(Class<?> paramType) {
        this.paramType = paramType;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public ParamProviderService getProviderService() {
        return providerService;
    }

    public void setProviderService(ParamProviderService providerService) {
        this.providerService = providerService;
    }

    public Class<? extends CacheDataKind> getClazzOfDataKind() {
        return clazzOfDataKind;
    }

    public void setClazzOfDataKind(Class<? extends CacheDataKind> clazzOfDataKind) {
        this.clazzOfDataKind = clazzOfDataKind;
    }

    public long getValidTime() {
        return validTime;
    }

    public void setValidTime(long validTime) {
        this.validTime = validTime;
    }

    public AtomicBoolean getThreadRunning() {
        return threadRunning;
    }

    public boolean isSensitiveKey() {
        return sensitiveKey;
    }

    public void setSensitiveKey(boolean sensitiveKey) {
        this.sensitiveKey = sensitiveKey;
    }

    public boolean getThreadRunningLock() {
        return threadRunning.compareAndSet(false, true);
    }

    public boolean getThreadRunningState() {
        return threadRunning.get();
    }

    public void resetThreadRunning() {
        this.threadRunning.set(false);
    }

    public <T extends ParamBean> List<T> loadData() {
        return this.providerService.list();
    }

    private Class<? extends CacheDataKind> createAndLoadClass() throws NotFoundException, CannotCompileException {
        ClassPool clazzPool = ClassPool.getDefault();
        CtClass ctClzz = clazzPool.makeClass("com.cgb.corebank.cachedatakind." + paramName);
        ctClzz.setSuperclass(clazzPool.get(ParamCacheKind.class.getName()));
        ctClzz.addConstructor(CtNewConstructor.defaultConstructor(ctClzz));
        return ctClzz.toClass();
    }

    public static void saveWarpClass(Class<?> clazz1) {
        String wrapCalssName = clazz1.getName()+"Wrap4ParamCenter";
        try {
            Class<? extends ParamBeanWrap> clazz2 = (Class<? extends ParamBeanWrap>) Class.forName(wrapCalssName);
            MAPS.put(clazz1, clazz2);
        } catch (ClassNotFoundException e) {
        }
    }

    public static Class<? extends ParamBeanWrap> generWarpClass(Class<?> clzz1) {
        return MAPS.get(clzz1);
    }
}
