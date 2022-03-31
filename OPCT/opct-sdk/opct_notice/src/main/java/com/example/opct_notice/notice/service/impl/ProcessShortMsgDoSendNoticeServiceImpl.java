//package com.example.opct_notice.notice.service.impl;
//
//import com.example.base.notice.dto.NoticeReqDTO;
//import com.example.base.notice.dto.NoticeRespDTO;
//import com.example.base.notice.enums.NoticeAppIdEnum;
//import com.example.base.notice.service.DoSendNoticeService;
//import com.example.base.notice.utils.NoticeUtil;
//import com.example.base.shortmsg.entity.ShortMsgDO;
//import org.springframework.stereotype.Component;
//
///**
// * @Author Administrator
// * @Date 2021/12/19 15:29
// */
//@Component(value = "ProcessShortMsgDoSendNoticeService")
//public class ProcessShortMsgDoSendNoticeServiceImpl implements DoSendNoticeService<ShortMsgDO, ShortMsgDO> {
//
//    @Override
//    public NoticeRespDTO<ShortMsgDO> doSendNotice(NoticeReqDTO<ShortMsgDO> request) {
//        NoticeRespDTO<ShortMsgDO> response = new NoticeRespDTO<ShortMsgDO>();
//        NoticeAppIdEnum blctNotice = NoticeAppIdEnum.BLCT_NOTICE;
//        int i = 0;
//        do {
////            response = rpc
//        } while ((null == response || null == response.getBody()) && i < 5);
//        if (null != response && null != response.getBody()) {
//            NoticeUtil.unserialzeNoticeResp(response, ShortMsgDO.class);
//        }
//        return response;
//    }
//}
