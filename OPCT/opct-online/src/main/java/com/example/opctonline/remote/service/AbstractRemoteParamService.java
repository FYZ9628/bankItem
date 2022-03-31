package com.example.opctonline.remote.service;

import com.example.base.param.ParamBean;

import java.util.List;

public interface AbstractRemoteParamService <T extends ParamBean>{

    /**
     * 根据主键查找
     * 组装：String.format("%s-%s",id1,id2);
     * 拆分：String[] key = id.split("-");
     * 去空格：key[0].trim();
     *
     * @param id
     * @return
     * @throws Exception
     */
    T getParamByKey(String id) throws Exception;

    /**
     * 加载所有参数
     * @return
     * @throws Exception
     */
    List<T> getAllParam() throws Exception;
}
