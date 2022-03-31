//package com.example.opct_notice.notice.service.impl;
//
//import com.example.base.notice.dto.NoticeReqDTO;
//import com.example.base.notice.dto.NoticeRespDTO;
//import com.example.base.notice.service.DoReceiveNoticeService;
//import com.example.base.notice.utils.NoticeUtil;
//import com.example.base.shortmsg.entity.ShortMsgDO;
//import org.springframework.stereotype.Component;
//
///**
// * @Author Administrator
// * @Date 2021/12/19 15:29
// */
//@Component(value = "ProcessShortMsgDoReceiveNoticeService")
//public class ProcessShortMsgDoReceiveNoticeServiceImpl implements DoReceiveNoticeService<ShortMsgDO, ShortMsgDO> {
//    @Override
//    public NoticeRespDTO<ShortMsgDO> doReceiveNotice(NoticeReqDTO<ShortMsgDO> request) {
//        NoticeRespDTO<ShortMsgDO> response = new NoticeRespDTO<ShortMsgDO>();
//        if (null != request && null != request.getBody()) {
//            NoticeUtil.unserialzeNoticeReq(request, ShortMsgDO.class);
//            ShortMsgDO shortMsgDO = request.getBody();
//            ShortMsgDO respBody = new ShortMsgDO();
//            response.setBody(respBody);
//            //加工短信
//        }
//        return response;
//    }
//}
