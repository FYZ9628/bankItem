package com.example.opct_notice.notice.dto;

/**
 * @Author Administrator
 * @Date 2021/12/19 15:25
 */
public class NoticeRespDTO<RespBody> {
    /**
     * 响应报文体
     */
    private RespBody body;

    public RespBody getBody() {
        return body;
    }

    public void setBody(RespBody body) {
        this.body = body;
    }
}
