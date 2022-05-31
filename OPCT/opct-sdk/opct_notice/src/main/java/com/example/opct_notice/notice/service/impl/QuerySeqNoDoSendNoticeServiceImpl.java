package com.example.opct_notice.notice.service.impl;

import com.example.base.utils.JudgeUtils;
import com.example.opct_base.sequence.entity.SeqNoDO;
import com.example.opct_notice.notice.constant.NoticeConst;
import com.example.opct_notice.notice.dto.NoticeReqDTO;
import com.example.opct_notice.notice.dto.NoticeRespDTO;
import com.example.opct_notice.notice.entity.QuerySeqNoReqBody;
import com.example.opct_notice.notice.entity.QuerySeqNoRespBody;
import com.example.opct_notice.notice.enums.NoticeAppIdEnum;
import com.example.opct_notice.notice.service.DoSendNoticeService;
import com.example.opct_notice.notice.utils.FeignServiceUtil;
import com.example.opct_notice.notice.utils.NoticeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component(value = NoticeConst.QUERY_SEQ_NO_DO_SEND_NOTICE)
public class QuerySeqNoDoSendNoticeServiceImpl implements DoSendNoticeService<QuerySeqNoReqBody, QuerySeqNoRespBody> {

    @Autowired
    private FeignServiceUtil feignServiceUtil;

    @Override
    public NoticeRespDTO<QuerySeqNoRespBody> doSendNotice(NoticeReqDTO<QuerySeqNoReqBody> request) throws Exception {
        NoticeRespDTO<QuerySeqNoRespBody> response = new NoticeRespDTO<>();
        List<SeqNoDO> allList = new ArrayList<>();
        QuerySeqNoRespBody respBody = new QuerySeqNoRespBody();
        QuerySeqNoReqBody reqBody = new QuerySeqNoReqBody();
        if (JudgeUtils.isNotNull(request) && JudgeUtils.isNotNull(request.getBody())) {
            reqBody = request.getBody();
        }
        for (NoticeAppIdEnum notice : NoticeAppIdEnum.values()) {
            if (JudgeUtils.isNull(notice.getServCode()) || JudgeUtils.notEquals(notice.getAppId(), reqBody.getAppId())) {
                continue;
            }

//            request.setHead(New BaseHead());
//            request.getHead().setServcCode(notice.getServCode());
            List<SeqNoDO> currentList = handle(request, response);
            allList.addAll(currentList);
        }
        respBody.setSeqNoDOList(allList);
        response.setBody(respBody);
        return response;
    }

    private List<SeqNoDO> handle(NoticeReqDTO<QuerySeqNoReqBody> request, NoticeRespDTO<QuerySeqNoRespBody> response) {
        List<SeqNoDO> currentList = new ArrayList<>();
        boolean lastPage = true;
        QuerySeqNoReqBody reqBody = new QuerySeqNoReqBody();
        do {
            if (JudgeUtils.isNull(request.getBody())) {
                reqBody.setCurrentPage(0);
                request.setBody(reqBody);
            }
            int i = 0;
            do {
//                response = RpcUtil.execute(request, NoticeRespDTO.class);

//                if (feignServiceUtil.getCurrentFeignService() != null) {
//                    response = feignServiceUtil.getCurrentFeignService().receiveNotice(request);
//                }
                if (feignServiceUtil.getFeignServiceByAppId(request.getBody().getAppId()) != null) {
                    response = feignServiceUtil.getFeignServiceByAppId(request.getBody().getAppId()).receiveNotice(request);
                }
//                response = opctFeignService.receiveNotice(request);
                i++;
                lastPage = true;
            } while ((JudgeUtils.isNull(response) || JudgeUtils.isNull(response.getBody())) && i < 5);

            if (JudgeUtils.isNotNull(response) && JudgeUtils.isNotNull(response.getBody())) {
                NoticeUtil.unserialzeNoticeResp(response, QuerySeqNoRespBody.class);
                if (JudgeUtils.isNotNull(response.getBody().getSeqNoDOList())) {
                    currentList.addAll(response.getBody().getSeqNoDOList());
                    reqBody.setCurrentPage(response.getBody().getCurrentPage());
                    reqBody.setAppId(request.getBody().getAppId());
                    reqBody.setSeqType(request.getBody().getSeqType());

                    request.setBody(reqBody);
                    lastPage = response.getBody().isLastPageFlag();
                }
            }
        } while (!lastPage);

        return currentList;
    }
}
