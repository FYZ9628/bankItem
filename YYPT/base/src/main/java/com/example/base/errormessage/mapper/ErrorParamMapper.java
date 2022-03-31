package com.example.base.errormessage.mapper;

import com.example.base.errormessage.entity.ErrorMessageDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ErrorParamMapper {
    List<ErrorMessageDO> list();
    ErrorMessageDO selectByKey(String errorInfoCode);
}
