package com.example.base.param.remote;

import com.example.base.param.ParamBean;
import com.example.base.param.annotation.CoreBankParam;

@CoreBankParam(paramName = BranchInfo.PARAM_NAME, local = false, loadAll = true, lazy = true)
public class BranchInfo implements ParamBean {
    public static final String PARAM_NAME = "BranchInfo";
    private String brNO;//机构号
    private String supBr;//上级机构号
    private String cnName;//中文名称
    private String enName;//英文名称
    private String brAbbr;//机构简称

    @Override
    public String uniqueKey() {
        return brNO;
    }

    public String getBrNO() {
        return brNO;
    }

    public void setBrNO(String brNO) {
        this.brNO = brNO;
    }

    public String getSupBr() {
        return supBr;
    }

    public void setSupBr(String supBr) {
        this.supBr = supBr;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getBrAbbr() {
        return brAbbr;
    }

    public void setBrAbbr(String brAbbr) {
        this.brAbbr = brAbbr;
    }
}
