package com.example.opct_notice.notice.service;

import com.example.opct_notice.notice.dto.NoticeReqDTO;
import com.example.opct_notice.notice.dto.NoticeRespDTO;

/**
 * @Author Administrator
 * @Date 2021/12/19 15:27
 */
public interface DoReceiveNoticeService<ReqBody, RespBody> {
    /**
     * 接收通知并处理通知逻辑实现
     * @param request
     * @return
     */
    NoticeRespDTO<RespBody> doReceiveNotice(NoticeReqDTO<ReqBody> request) throws Exception;
}
