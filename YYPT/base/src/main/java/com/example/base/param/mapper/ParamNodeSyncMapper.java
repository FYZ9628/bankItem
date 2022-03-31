package com.example.base.param.mapper;

import com.example.base.param.entity.ParamNodeBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//@Mapper
public interface ParamNodeSyncMapper extends ParamMapper<ParamNodeBean> {
    int updateNodeInfo(ParamNodeBean nodeBean);
    int insertNodeInfo(ParamNodeBean nodeBean);
    int insertMainNodeInfo(@Param("paraCode") String paraCode);
    int queryNodeExits(ParamNodeBean nodeBean);
    List<ParamNodeBean> queryNodeInfo(@Param("paraCode") String paraCode);
    List<ParamNodeBean> queryMyNodeAndMainNode(@Param("nodeName") String nodeName);
}
