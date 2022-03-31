package com.example.base.param.remote.dto;

import com.example.base.param.ParamBean;

import java.util.List;

public class RemoteParamQueryRespDTO<T extends ParamBean> {
    /**
     * 按单个请求时 返回
     */
    private T paramBean;
    /**
     * 按全部请求时返回
     */
    private List<T> listOfParamBean;

    public T getParamBean() {
        return paramBean;
    }

    public void setParamBean(T paramBean) {
        this.paramBean = paramBean;
    }

    public List<T> getListOfParamBean() {
        return listOfParamBean;
    }

    public void setListOfParamBean(List<T> listOfParamBean) {
        this.listOfParamBean = listOfParamBean;
    }
}
