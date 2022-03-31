package com.example.base.errormessage.service.impl;

import com.example.base.errormessage.entity.ErrorMessageDO;
import com.example.base.errormessage.mapper.ErrorParamMapper;
import com.example.base.errormessage.service.ErrorParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 错误码信息服务
 */
@Service
public class ErrorParamServiceImpl implements ErrorParamService {
    @Autowired
    private ErrorParamMapper errorParamMapper;

    /**
     * 查询错误码信息
     * @return
     */
    @Override
    public List<ErrorMessageDO> getAllErrorMessage() {
        return errorParamMapper.list();
    }

    /**
     * 加载所有参数
     * @return
     */
    @Override
    public List<ErrorMessageDO> list() {
        return errorParamMapper.list();
    }

    /**
     * 根据key查找
     * @param key
     * @return
     */
    @Override
    public ErrorMessageDO getByKey(Object key) {
        return errorParamMapper.selectByKey((String)key);
    }
}
