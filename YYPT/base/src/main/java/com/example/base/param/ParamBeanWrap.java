package com.example.base.param;

public interface ParamBeanWrap<T> {
    void setTarget(T t);
    void modifySetFlag(boolean flag);
}
