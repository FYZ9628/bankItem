package com.example.opct_notice.base.sequence.service;

import com.example.opct_base.sequence.entity.SeqNoDO;

import java.util.List;

public interface SeqNoService {
    /**
     * 根据appId 和 顺序号类型查询数据
     *
     * @param appId
     * @param seqType
     * @return
     */
    List<SeqNoDO> getAllSeqNo(String appId, String seqType);
}
