package com.example.base.cache.impl;

import com.example.base.cache.*;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.function.Function;

/**
 * Redis 缓存实现
 */
public class RedisCacheImpl implements ICoreCache {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCacheImpl.class);
    //初始化redis的执行线程,初始值 1，最大值为10，使用new LinkedBlockingQueue<>(),
    private static final ExecutorService executorService = new ScheduledThreadPoolExecutor(1,new ThreadFactoryBuilder().setNameFormat("RedisCache-Thread").build());
//    //key生成器   SpiLoader 加载器
//    private CachekeyGenerator keyGenerator = RtpSpiLoader.getSpiLoader(CachekeyGenerator.class).get();
//    //（反）序列化器
//    private Serializer<Object, String> serializer = RtpSpiLoader.getSpiLoader(Serializer.class).get();
    @Override
    public void cache(CacheData cacheData) {
        try {
//            set(cacheData);
        } catch (Exception e) {
            LOGGER.warn("(retry async) fail to set key:{}, value:{}, data:{}", cacheData.getKey(),cacheData.getValue(), cacheData);
//            executorService.submit(new CallableTask<>(cacheData, (c) ->set(c)));
        }
    }

//    private String set(CacheData<?> cacheData) {
//        String key = keyGenerator.getKey(cacheData.getKey(), cacheData.getCacheKindClazz());
//        String value = serializer.serialize(cacheData.getValue());
//        if (cacheData.getTimeout() >= 0) {
//            return RedisUtil.psetex(key, cacheData.getTimeout(), value);
//        } else {
//            return ReidsUtil.set(key, value);
//        }
//    }

    @Override
    public <T> T get(String key, Class<? extends CacheDataKind> clazz) {
//        String redisKey =keyGenerator.getKey(key, clazz);
//        String result = RedisUtil.get(redisKey);
//        return (T)serializer.deSerialize(result);
        return null;
    }

    @Override
    public void remove(String key, Class<? extends CacheDataKind> clazz) {
        DeLEntity deLEntity = new DeLEntity(key, clazz);
        try {
//            del(deLEntity);
        } catch (Exception e) {
            LOGGER.warn("(retry async)fail to remove key: {}, clazz: {}", key, clazz);
//            executorService.submit(new CallableTask<>(deLEntity, (d) -> del(d)));
        }

    }

//    private long del(DeLEntity entity) {
//        String redisKey = keyGenerator.getKey(entity.getKey(), entity.getClazz());
//        long waste = RedisUtil.del(redisKey);
//        return waste;
//    }

    class DeLEntity {
        private String key;
        private Class<? extends CacheDataKind> clazz;

        public DeLEntity(String key, Class<? extends CacheDataKind> clazz) {
            this.key = key;
            this.clazz = clazz;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Class<? extends CacheDataKind> getClazz() {
            return clazz;
        }

        public void setClazz(Class<? extends CacheDataKind> clazz) {
            this.clazz = clazz;
        }
    }

    class CallableTask<T, R> implements Runnable {
        private char times;
        private Function<T, R> function;
        private T t;

        public CallableTask(T cacheData, Function<T, R> function) {
            this.t = cacheData;
            this.function = function;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                times++;
                function.apply(t);
            } catch (Exception e) {
                if (times > 2) {
                    LOGGER.error("after retry 2 times, fail to apply function: {} for t : {}", function, t, e);
                } else {
                    executorService.submit(this);
                    LOGGER.error("(retry async) retry {} times, fail to apply function: {} for t : {}", times, function, t, e);
                }
            }
        }
    }
}
