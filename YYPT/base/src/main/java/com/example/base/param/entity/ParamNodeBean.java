package com.example.base.param.entity;

import com.example.base.param.ParamBean;

public class ParamNodeBean implements ParamBean {
    private String rid;
    private String paraCode;
    private String nodeName;
    private String lastCacheTime;

    public ParamNodeBean() {
    }

    public ParamNodeBean(String paraCode, String nodeName) {
        this.paraCode = paraCode;
        this.nodeName = nodeName;
    }

    @Override
    public String uniqueKey() {
        return rid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getParaCode() {
        return paraCode;
    }

    public void setParaCode(String paraCode) {
        this.paraCode = paraCode;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getLastCacheTime() {
        return lastCacheTime;
    }

    public void setLastCacheTime(String lastCacheTime) {
        this.lastCacheTime = lastCacheTime;
    }

    @Override
    public String toString() {
        return "ParamNodeBean{" +
                "rid='" + rid + '\'' +
                ", paraCode='" + paraCode + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", lastCacheTime='" + lastCacheTime + '\'' +
                '}';
    }
}
