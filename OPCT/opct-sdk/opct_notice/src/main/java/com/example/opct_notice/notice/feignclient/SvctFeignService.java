package com.example.opct_notice.notice.feignclient;

import com.example.opct_notice.notice.dto.NoticeReqDTO;
import com.example.opct_notice.notice.dto.NoticeRespDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@FeignClient(name = "SVCT-ONLINE")
public interface SvctFeignService extends FeignService {
    @RequestMapping(value = "/svct/svctReceiveNotice")
    @Override
    NoticeRespDTO receiveNotice(NoticeReqDTO request);
}
