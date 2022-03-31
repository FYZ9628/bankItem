package com.example.opct_notice.base.errormessage.service.impl;

import com.example.base.errormessage.entity.ErrorMessageDO;
import com.example.opct_notice.base.errormessage.mapper.ErrorMessageMapper;
import com.example.opct_notice.base.errormessage.service.ErrorMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ErrorMessageServiceImpl implements ErrorMessageService {
    @Autowired
    private ErrorMessageMapper errorMessageMapper;

    @Override
    public List<ErrorMessageDO> list() {
        return errorMessageMapper.list();
    }

    @Override
    public ErrorMessageDO selectByKey(String errorInfoCode) {
        return errorMessageMapper.selectByKey(errorInfoCode);
    }

    @Override
    public void create(ErrorMessageDO errorMessageDO) {
        errorMessageMapper.create(errorMessageDO);
    }

    @Override
    public void createByBatch(List<ErrorMessageDO> list) {
        errorMessageMapper.createByBatch(list);
    }

    @Override
    public void update(ErrorMessageDO errorMessageDO) {
        errorMessageMapper.update(errorMessageDO);
    }

    @Override
    public void delete(String errorInfoCode) {
        errorMessageMapper.delete(errorInfoCode);
    }

    @Override
    public List<ErrorMessageDO> listBySyncFlag(String syncFlag) {
        return errorMessageMapper.listBySyncFlag(syncFlag);
    }
}
