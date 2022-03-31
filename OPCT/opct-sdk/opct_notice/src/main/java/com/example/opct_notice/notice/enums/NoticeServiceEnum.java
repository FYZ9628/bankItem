package com.example.opct_notice.notice.enums;

import com.example.opct_notice.notice.constant.NoticeConst;

/**
 * @Author Administrator
 * @Date 2021/12/19 15:26
 */
public enum NoticeServiceEnum {
    /**
     * 查询顺序号信息服务
     */
    QUERY_SEQ_NO(NoticeConst.QUERY_SEQ_NO_DO_SEND_NOTICE, NoticeConst.QUERY_SEQ_NO_DO_RECEIVE_NOTICE),

    /**
     * 错误信息码同步-运营中心发送通知服务-其他中心接收
     */
    ERROR_MSG_OPCT_SEND(NoticeConst.ERROR_MSG_OPCT_DO_SEND_NOTICE, NoticeConst.ERROR_MSG_DO_RECEIVE_NOTICE),

    /**
     * 错误信息码同步-其他中心发送通知服务-运营中心接收
     */
    ERROR_MSG_OTHERS_SEND(NoticeConst.ERROR_MSG_OTHERS_DO_SEND_NOTICE, NoticeConst.ERROR_MSG_DO_RECEIVE_NOTICE),

    /**
     * 发送短信（待处理短信）
     */
    SEND_SHORTMSG("", ""),

    /**
     * 加工短信
     */
    PROCESS_SHORTMSG("ProcessShortMsgDoSendNoticeService", "ProcessShortMsgDoReceiveNoticeService"),
    ;

    NoticeServiceEnum(String doSendNoticeService, String doReceiveNoticeService) {
        this.doSendNoticeService = doSendNoticeService;
        this.doReceiveNoticeService = doReceiveNoticeService;
    }

    /**
     * 发送通知逻辑实现服务
     */
    private String doSendNoticeService;

    /**
     * 接收通知并处理通知逻辑实现
     */
    private String doReceiveNoticeService;

    public String getDoSendNoticeService() {
        return doSendNoticeService;
    }

    public void setDoSendNoticeService(String doSendNoticeService) {
        this.doSendNoticeService = doSendNoticeService;
    }

    public String getDoReceiveNoticeService() {
        return doReceiveNoticeService;
    }

    public void setDoReceiveNoticeService(String doReceiveNoticeService) {
        this.doReceiveNoticeService = doReceiveNoticeService;
    }
}
