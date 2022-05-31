package com.example.opctonline.controller.impl;

import com.example.opct_notice.notice.dto.NoticeReqDTO;
import com.example.opct_notice.notice.dto.NoticeRespDTO;
import com.example.opct_notice.notice.service.impl.AbstractGeneralNoticeService;
import com.example.opctonline.controller.NoticeController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticeControllerImpl extends AbstractGeneralNoticeService implements NoticeController {
    @RequestMapping("/opctReceiveNotice")
    @Override
    public NoticeRespDTO receiveNotice(@RequestBody NoticeReqDTO request) throws Exception {
        return doReceiveNotice(request);
    }

}
