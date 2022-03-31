package com.example.opct_notice.base.errormessage.mapper;

import com.example.base.errormessage.entity.ErrorMessageDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ErrorMessageMapper {
    List<ErrorMessageDO> list();
    ErrorMessageDO selectByKey(String errorInfoCode);
    int create(ErrorMessageDO errorMessageDO);
    int createByBatch(List<ErrorMessageDO> list);
    int update(ErrorMessageDO errorMessageDO);
    int delete(String errorInfoCode);
    List<ErrorMessageDO> listBySyncFlag(String syncFlag);
}
