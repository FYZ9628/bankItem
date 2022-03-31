package com.example.base.param.annotation;

import com.example.base.param.CustomerCacheProvider;
import com.example.base.param.provider.ParamProviderService;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CoreBankParam {
    /**
     * 是否懒加载
     * @return
     */
    boolean lazy() default (false);

    /**
     * 是否进行缓存
     * @return
     */
    boolean cache() default (true);

    /**
     * 是否全量加载
     * @return
     */
    boolean loadAll() default (false);

    /**
     * 参数服务提供者
     * @return
     */
    Class<? extends ParamProviderService> provider() default ParamProviderService.class;

    /**
     * 本地参数
     * @return
     */
    boolean local() default (true);

    /**
     * 参数名称
     * @return
     */
    String paramName();

    /**
     * 有效时间 默认-1永久有效，单位毫秒
     * @return
     */
    long validTime() default (-1);

    /**
     * 自定义缓存逻辑
     * @return
     */
    Class<? extends CustomerCacheProvider> customCacheProvider() default CustomerCacheProvider.class;

    /**
     * 存入缓存的key是否敏感字段
     * @return
     */
    boolean sensitiveKey() default (false);
}
