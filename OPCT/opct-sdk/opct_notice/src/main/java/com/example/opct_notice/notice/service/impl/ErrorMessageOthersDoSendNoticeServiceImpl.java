package com.example.opct_notice.notice.service.impl;

import com.example.base.utils.JudgeUtils;
import com.example.opct_notice.notice.constant.NoticeConst;
import com.example.opct_notice.notice.dto.NoticeReqDTO;
import com.example.opct_notice.notice.dto.NoticeRespDTO;
import com.example.opct_notice.notice.entity.ErrorMessageReqBody;
import com.example.opct_notice.notice.enums.NoticeAppIdEnum;
import com.example.opct_notice.notice.service.DoSendNoticeService;
import org.springframework.stereotype.Component;

/**
 * 错误信息码同步-其他中心发送通知服务
 */
@Component(value = NoticeConst.ERROR_MSG_OTHERS_DO_SEND_NOTICE)
public class ErrorMessageOthersDoSendNoticeServiceImpl implements DoSendNoticeService<ErrorMessageReqBody, Boolean> {
    /**
     * 发送通知
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public NoticeRespDTO<Boolean> doSendNotice(NoticeReqDTO<ErrorMessageReqBody> request) throws Exception {
        NoticeRespDTO<Boolean> response = new NoticeRespDTO<Boolean>();
        int i = 0;
        NoticeAppIdEnum opctNotice = NoticeAppIdEnum.OPCT_NOTICE;
//        request.setHead(new BaseHead());
//        request.getHead().setServcCode(opctNotice.getServCode());
        do {
//            response = RpcUtil.execute(request, NoticeRespDTO.class);
            i++;
        } while ((JudgeUtils.isNull(response) || JudgeUtils.isNull(response.getBody()) || !response.getBody()) && i<5);

        return response;
    }
}
