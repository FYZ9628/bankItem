package com.example.base.errormessage.service;

import com.example.base.errormessage.entity.ErrorMessageDO;
import com.example.base.param.ParamBean;
import com.example.base.param.provider.ParamProviderService;

import java.util.List;

/**
 * 错误码信息服务
 * @param <T>
 */
public interface ErrorParamService <T extends ParamBean> extends ParamProviderService {
    /**
     * 查询错误码信息
     * @return
     */
    List<ErrorMessageDO> getAllErrorMessage();
}
