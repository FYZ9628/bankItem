package com.example.opct_base.sequence.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 顺序号DO
 */
public class SeqNoDO {
    /**
     * 顺序号类型
     */
    private String seqType;
    /**
     * 顺序号代码
     */
    private String seqCode;
    /**
     * 顺序号描述
     */
    private String seqDesc;
    /**
     * 当前使用顺序号
     */
    private Long curUseSeqNo;
    /**
     * 初始顺序号
     */
    private Long initSeqNo;
    /**
     * 顺序号步长
     */
    private Integer seqNoStep;
    /**
     * 最大顺序号
     */
    private Long maxSeqNo;
    /**
     * 预警顺序号
     */
    private Long earlyWarnSeqNo;
    /**
     * 创建时间戳
     */
    @JSONField(format = "yyyyMMddHHmmss")
    private Date createTs;
    /**
     * 更新时间戳
     */
    @JSONField(format = "yyyyMMddHHmmss")
    private Date updateTs;

    public String getSeqType() {
        return seqType;
    }

    public void setSeqType(String seqType) {
        this.seqType = seqType;
    }

    public String getSeqCode() {
        return seqCode;
    }

    public void setSeqCode(String seqCode) {
        this.seqCode = seqCode;
    }

    public String getSeqDesc() {
        return seqDesc;
    }

    public void setSeqDesc(String seqDesc) {
        this.seqDesc = seqDesc;
    }

    public Long getCurUseSeqNo() {
        return curUseSeqNo;
    }

    public void setCurUseSeqNo(Long curUseSeqNo) {
        this.curUseSeqNo = curUseSeqNo;
    }

    public Long getInitSeqNo() {
        return initSeqNo;
    }

    public void setInitSeqNo(Long initSeqNo) {
        this.initSeqNo = initSeqNo;
    }

    public Integer getSeqNoStep() {
        return seqNoStep;
    }

    public void setSeqNoStep(Integer seqNoStep) {
        this.seqNoStep = seqNoStep;
    }

    public Long getMaxSeqNo() {
        return maxSeqNo;
    }

    public void setMaxSeqNo(Long maxSeqNo) {
        this.maxSeqNo = maxSeqNo;
    }

    public Long getEarlyWarnSeqNo() {
        return earlyWarnSeqNo;
    }

    public void setEarlyWarnSeqNo(Long earlyWarnSeqNo) {
        this.earlyWarnSeqNo = earlyWarnSeqNo;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public Date getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
    }
}
