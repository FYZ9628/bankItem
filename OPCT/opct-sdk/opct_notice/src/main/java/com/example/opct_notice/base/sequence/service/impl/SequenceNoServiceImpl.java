package com.example.opct_notice.base.sequence.service.impl;

import com.example.opct_base.sequence.entity.SeqNoDO;
import com.example.opct_base.sequence.service.SequenceNoService;
import com.example.opct_notice.base.sequence.mapper.SequenceNoMapper;
import com.example.opct_notice.notice.dto.NoticeReqDTO;
import com.example.opct_notice.notice.dto.NoticeRespDTO;
import com.example.opct_notice.notice.entity.QuerySeqNoReqBody;
import com.example.opct_notice.notice.entity.QuerySeqNoRespBody;
import com.example.opct_notice.notice.enums.NoticeServiceEnum;
import com.example.opct_notice.notice.service.impl.AbstractGeneralNoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SequenceNoServiceImpl implements SequenceNoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SequenceNoServiceImpl.class);

    @Autowired
    private SequenceNoMapper sequenceNoMapper;

    @Override
    public List<SeqNoDO> listBySeqType(String seqType) {
        SeqNoDO seqNoDO = new SeqNoDO();
        seqNoDO.setSeqType(seqType);
        return sequenceNoMapper.listBySeqType(seqNoDO);
    }
}
