package com.example.opct_notice.notice.dto;

import com.example.opct_notice.notice.enums.NoticeServiceEnum;

/**
 * @Author Administrator
 * @Date 2021/12/19 15:24
 */
public class NoticeReqDTO<ReqBody> {
    /**
     * 通知服务
     */
    private NoticeServiceEnum noticeServiceEnum;

    /**
     * 接收服务
     */
    private String doReceiveNoticeService;

    /**
     * 请求报文体
     */
    private ReqBody body;

    public NoticeServiceEnum getNoticeServiceEnum() {
        return noticeServiceEnum;
    }

    public void setNoticeServiceEnum(NoticeServiceEnum noticeServiceEnum) {
        this.noticeServiceEnum = noticeServiceEnum;
    }

    public String getDoReceiveNoticeService() {
        return doReceiveNoticeService;
    }

    public void setDoReceiveNoticeService(String doReceiveNoticeService) {
        this.doReceiveNoticeService = doReceiveNoticeService;
    }

    public ReqBody getBody() {
        return body;
    }

    public void setBody(ReqBody body) {
        this.body = body;
    }
}
