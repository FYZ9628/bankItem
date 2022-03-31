package com.example.base.param.remote.dto;

public class RemoteParamQueryReqDTO {
    /**
     * 请求参数名称
     */
    private String paramName;
    /**
     * 参数key，用于单个根据key加载
     */
    private String key;
    /**
     * 参数加载类型，根据key 或 全量加载
     */
    private String loadType;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLoadType() {
        return loadType;
    }

    public void setLoadType(String loadType) {
        this.loadType = loadType;
    }
}
