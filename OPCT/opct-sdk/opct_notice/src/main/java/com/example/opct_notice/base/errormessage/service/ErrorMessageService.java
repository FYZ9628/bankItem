package com.example.opct_notice.base.errormessage.service;

import com.example.base.errormessage.entity.ErrorMessageDO;

import java.util.List;

public interface ErrorMessageService {
    List<ErrorMessageDO> list();
    ErrorMessageDO selectByKey(String errorInfoCode);
    void create(ErrorMessageDO errorMessageDO);
    void createByBatch(List<ErrorMessageDO> list);
    void update(ErrorMessageDO errorMessageDO);
    void delete(String errorInfoCode);
    List<ErrorMessageDO> listBySyncFlag(String syncFlag);
}
