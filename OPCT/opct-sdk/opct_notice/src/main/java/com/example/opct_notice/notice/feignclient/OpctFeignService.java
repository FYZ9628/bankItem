package com.example.opct_notice.notice.feignclient;

import com.example.opct_notice.notice.dto.NoticeReqDTO;
import com.example.opct_notice.notice.dto.NoticeRespDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@FeignClient(name = "OPCT-ONLINE")
public interface OpctFeignService extends FeignService {
    @RequestMapping(value = "/opct/opctReceiveNotice")
    @Override
    NoticeRespDTO receiveNotice(NoticeReqDTO request);
}
