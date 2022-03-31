package com.example.opct_notice.notice.service;

import com.example.opct_notice.notice.dto.NoticeReqDTO;
import com.example.opct_notice.notice.dto.NoticeRespDTO;

/**
 * @Author Administrator
 * @Date 2021/12/19 15:27
 */
public interface DoSendNoticeService<ReqBody, RespBody> {
    /**
     * 发送通知逻辑实现
     * @param request
     * @return
     */
    NoticeRespDTO<RespBody> doSendNotice(NoticeReqDTO<ReqBody> request) throws Exception;
}
