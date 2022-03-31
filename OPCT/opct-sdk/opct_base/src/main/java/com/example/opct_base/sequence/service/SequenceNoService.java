package com.example.opct_base.sequence.service;

import com.example.opct_base.sequence.entity.SeqNoDO;

import java.util.List;

/**
 * 顺序号服务
 */
public interface SequenceNoService {
    /**
     * 根据顺序号类型查询
     * @param seqType
     * @return
     */
    List<SeqNoDO> listBySeqType(String seqType);

//    /**
//     * 根据appId 和 顺序号类型查询数据
//     *
//     * @param appId
//     * @param seqType
//     * @return
//     */
//    List<SeqNoDO> getAllSeqNo(String appId, String seqType);
}
