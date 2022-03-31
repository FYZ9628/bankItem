package com.example.base.param.mapper;

import com.example.base.param.ParamBean;

import java.util.List;

public interface ParamMapper<T extends ParamBean> {
    List<T> list();
    int update(T t);
    int insert(T t);
}
