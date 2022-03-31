package com.example.opct_notice.notice.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 查询顺序表信息请求体
 */
public class QuerySeqNoReqBody {
    /**
     * 当前页
     */
    @JSONField(format = "currentPage")
    private Integer currentPage = 0;
    /**
     * 应用ID
     */
    private String appId = "";
    /**
     * 顺序号类型
     */
    private String seqType = "";

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSeqType() {
        return seqType;
    }

    public void setSeqType(String seqType) {
        this.seqType = seqType;
    }
}
