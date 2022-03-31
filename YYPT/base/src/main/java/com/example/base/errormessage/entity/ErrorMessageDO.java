package com.example.base.errormessage.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.base.errormessage.service.ErrorParamService;
import com.example.base.param.ParamBean;
import com.example.base.param.annotation.CoreBankParam;

import java.util.Date;

@CoreBankParam(paramName = ErrorMessageDO.PARAM_NAME, local = true, provider = ErrorParamService.class)
public class ErrorMessageDO implements ParamBean {
    private static final long serialVersionUID = 5299564182533606103L;
    public static final String PARAM_NAME = "ErrorMessage";

    /**
     * 错误信息码
     */
    private String errorInfoCode;
    /**
     * 错误码名称
     */
    private String errorCodeName;
    /**
     * 应用ID
     */
    private String appId;
    /**
     * 错误码类型
     */
    private String errorCodeType;
    /**
     * 同步标志(1-已同步，0-未同步)
     */
    private String syncFlag = "0";
    /**
     * 中文描述
     */
    private String cnDesc;
    /**
     * 英文描述
     */
    private String enDesc;

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

    @Override
    public String uniqueKey() {
        return this.errorInfoCode;
    }

    public String getErrorInfoCode() {
        return errorInfoCode;
    }

    public void setErrorInfoCode(String errorInfoCode) {
        this.errorInfoCode = errorInfoCode;
    }

    public String getErrorCodeName() {
        return errorCodeName;
    }

    public void setErrorCodeName(String errorCodeName) {
        this.errorCodeName = errorCodeName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getErrorCodeType() {
        return errorCodeType;
    }

    public void setErrorCodeType(String errorCodeType) {
        this.errorCodeType = errorCodeType;
    }

    public String getSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag(String syncFlag) {
        this.syncFlag = syncFlag;
    }

    public String getCnDesc() {
        return cnDesc;
    }

    public void setCnDesc(String cnDesc) {
        this.cnDesc = cnDesc;
    }

    public String getEnDesc() {
        return enDesc;
    }

    public void setEnDesc(String enDesc) {
        this.enDesc = enDesc;
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
