package com.example.opct_notice.notice.service.impl;

import com.example.base.utils.JudgeUtils;
import com.example.opct_base.sequence.entity.SeqNoDO;
import com.example.opct_base.sequence.service.SequenceNoService;
import com.example.opct_notice.notice.constant.NoticeConst;
import com.example.opct_notice.notice.dto.NoticeReqDTO;
import com.example.opct_notice.notice.dto.NoticeRespDTO;
import com.example.opct_notice.notice.entity.QuerySeqNoReqBody;
import com.example.opct_notice.notice.entity.QuerySeqNoRespBody;
import com.example.opct_notice.notice.service.DoReceiveNoticeService;
import com.example.opct_notice.notice.utils.NoticeUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = NoticeConst.QUERY_SEQ_NO_DO_RECEIVE_NOTICE)
public class QuerySeqNoDoReceiveNoticeServiceImpl implements DoReceiveNoticeService<QuerySeqNoReqBody, QuerySeqNoRespBody> {
    @Autowired
    private SequenceNoService sequenceNoService;

    @Override
    public NoticeRespDTO<QuerySeqNoRespBody> doReceiveNotice(NoticeReqDTO<QuerySeqNoReqBody> request) throws Exception {
        NoticeRespDTO<QuerySeqNoRespBody> response = new NoticeRespDTO<>();
        QuerySeqNoRespBody respBody = new QuerySeqNoRespBody();

        if (JudgeUtils.isNotNull(request) && JudgeUtils.isNotNull(request.getBody())) {
            NoticeUtil.unserialzeNoticeReq(request, QuerySeqNoReqBody.class);
            QuerySeqNoReqBody reqBody = request.getBody();
            int pageNum = reqBody.getCurrentPage() + 1;
            PageHelper.startPage(pageNum, 500);
            List<SeqNoDO> seqNoDOList = sequenceNoService.listBySeqType(reqBody.getSeqType());
            PageInfo<SeqNoDO> pageInfo = new PageInfo<SeqNoDO>(seqNoDOList);
            int pages = pageInfo.getPages();

            if (JudgeUtils.isNotNull(seqNoDOList)) {
                respBody.setSeqNoDOList(seqNoDOList);
                if (pageNum < pages) {
                    respBody.setLastPageFlag(false);
                } else {
                    respBody.setLastPageFlag(true);
                }
                respBody.setCurrentPage(pageNum);
            }
        }
        response.setBody(respBody);

        return response;
    }
}
