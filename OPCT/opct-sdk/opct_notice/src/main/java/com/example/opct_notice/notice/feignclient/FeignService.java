package com.example.opct_notice.notice.feignclient;

import com.example.opct_notice.notice.dto.NoticeReqDTO;
import com.example.opct_notice.notice.dto.NoticeRespDTO;

public interface FeignService {
    NoticeRespDTO receiveNotice(NoticeReqDTO request);
}
