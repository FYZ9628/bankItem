package com.example.base.param.provider;

import com.example.base.param.ParamBean;

import java.util.List;

public interface ParamProviderService<T extends ParamBean> {
    /**
     * 加载所有参数
     * @return
     */
    List<T> list();

    /**
     * 根据key查找
     * @param key
     * @return
     */
    T getByKey(Object key);
}
