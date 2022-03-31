package com.example.opct_notice.notice.service;

import com.example.opct_notice.notice.dto.NoticeReqDTO;
import com.example.opct_notice.notice.dto.NoticeRespDTO;

/**
 * @Author Administrator
 * @Date 2021/12/19 15:26
 */
public interface GeneralNoticeService<ReqBody, RespBody> {
    /**
     * 发送通知服务入口
     * @param request
     * @return
     */
    NoticeRespDTO<RespBody> sendNotice(NoticeReqDTO<ReqBody> request) throws Exception;

    /**
     * 发送通知逻辑实现
     * @param request
     * @return
     */
    NoticeRespDTO<RespBody> doSendNotice(NoticeReqDTO<ReqBody> request) throws Exception;

    /**
     * 接收通知服务入口
     * @param request
     * @return
     */
    NoticeRespDTO<RespBody> receiveNotice(NoticeReqDTO<ReqBody> request) throws Exception;

    /**
     * 接收通知并处理通知逻辑实现
     * @param request
     * @return
     */
    NoticeRespDTO<RespBody> doReceiveNotice(NoticeReqDTO<ReqBody> request) throws Exception;

}
