package com.example.opct_notice.notice.service.impl;

import com.example.opct_notice.notice.dto.NoticeReqDTO;
import com.example.opct_notice.notice.dto.NoticeRespDTO;
import com.example.opct_notice.notice.enums.NoticeServiceEnum;
import com.example.opct_notice.notice.service.DoReceiveNoticeService;
import com.example.opct_notice.notice.service.DoSendNoticeService;
import com.example.opct_notice.notice.service.GeneralNoticeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @Author Administrator
 * @Date 2021/12/19 15:28
 */
public abstract class AbstractGeneralNoticeService implements GeneralNoticeService {
    /**
     * 通知发送服务
     */
    @Autowired
    private Map<String, DoSendNoticeService> doSendNoticeServiceMap;

    /**
     * 通知处理服务
     */
    @Autowired
    private Map<String, DoReceiveNoticeService> doReceiveNoticeServiceMap;

    /**
     * 发送通知服务入口
     * @param request
     * @return
     */
    @Override
    public NoticeRespDTO sendNotice(NoticeReqDTO request) throws Exception {
        return null;
    }

    /**
     * 发送通知逻辑实现
     * @param request
     * @return
     */
    @Override
    public NoticeRespDTO doSendNotice(NoticeReqDTO request) throws Exception {
        NoticeRespDTO response = new NoticeRespDTO();
        NoticeServiceEnum noticeServiceEnum = request.getNoticeServiceEnum();
        request.setDoReceiveNoticeService(noticeServiceEnum.getDoReceiveNoticeService());
        DoSendNoticeService doSendNoticeService = doSendNoticeServiceMap.get(noticeServiceEnum.getDoSendNoticeService());
        if (null == doSendNoticeService) {
            throw new Exception("发送通知没找到");
        }
        if (null != doSendNoticeService) {
            request.setNoticeServiceEnum(null);
            response = doSendNoticeService.doSendNotice(request);
        }
        return response;
    }

    /**
     * 接收通知服务入口
     * @param request
     * @return
     */
    @Override
    public NoticeRespDTO receiveNotice(NoticeReqDTO request) throws Exception {
        return null;
    }

    /**
     * 接收通知并处理通知逻辑实现
     * @param request
     * @return
     */
    @Override
    public NoticeRespDTO doReceiveNotice(NoticeReqDTO request) throws Exception {
        NoticeRespDTO response = new NoticeRespDTO();
        DoReceiveNoticeService doReceiveNoticeService = doReceiveNoticeServiceMap.get(request.getDoReceiveNoticeService());
        if (null == doReceiveNoticeService) {
            throw new Exception("处理通知没找到");
        }
        if (null != doReceiveNoticeService) {
            response = doReceiveNoticeService.doReceiveNotice(request);
        }
        return response;
    }
}
