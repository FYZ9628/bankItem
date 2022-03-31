package com.example.opct_notice.base.sequence.service.impl;

import com.example.opct_base.sequence.entity.SeqNoDO;
import com.example.opct_notice.base.sequence.service.SeqNoService;
import com.example.opct_notice.notice.dto.NoticeReqDTO;
import com.example.opct_notice.notice.dto.NoticeRespDTO;
import com.example.opct_notice.notice.entity.QuerySeqNoReqBody;
import com.example.opct_notice.notice.entity.QuerySeqNoRespBody;
import com.example.opct_notice.notice.enums.NoticeServiceEnum;
import com.example.opct_notice.notice.service.impl.AbstractGeneralNoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
@Component
public class SeqNoServiceImpl extends AbstractGeneralNoticeService implements SeqNoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeqNoServiceImpl.class);

    @Override
    public List<SeqNoDO> getAllSeqNo(String appId, String seqType) {
        NoticeReqDTO<QuerySeqNoReqBody> request = new NoticeReqDTO<>();
        QuerySeqNoReqBody reqBody = new QuerySeqNoReqBody();
        reqBody.setAppId(appId);
        reqBody.setSeqType(seqType);
        reqBody.setCurrentPage(0);

        request.setNoticeServiceEnum(NoticeServiceEnum.QUERY_SEQ_NO);
        request.setBody(reqBody);
        NoticeRespDTO<QuerySeqNoRespBody> response = new NoticeRespDTO<>();
        try {
            response = this.doSendNotice(request);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("发送通知失败：{}", e);
        }

        return response.getBody().getSeqNoDOList();
    }
}
