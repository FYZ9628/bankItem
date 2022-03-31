package com.example.base.param.entity;

import com.example.base.param.ParamBean;

public class NullParamBean implements ParamBean {
    @Override
    public String uniqueKey() {
        return null;
    }
}
