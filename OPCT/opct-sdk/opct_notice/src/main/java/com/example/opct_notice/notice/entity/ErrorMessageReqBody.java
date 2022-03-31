package com.example.opct_notice.notice.entity;

import com.example.base.errormessage.entity.ErrorMessageDO;

import java.util.ArrayList;
import java.util.List;

/**
 * 错误信息码请求报文体
 */
public class ErrorMessageReqBody {
    /**
     * 错误信息码数组
     */
    private List<ErrorMessageDO> doList = new ArrayList<>();
    /**
     * 操作类型
     * C-创建
     * U-更新
     * D-删除
     */
    private String operateType;

    public List<ErrorMessageDO> getDoList() {
        return doList;
    }

    public void setDoList(List<ErrorMessageDO> doList) {
        this.doList = doList;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }
}
