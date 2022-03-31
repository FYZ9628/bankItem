package com.example.opct_notice.notice.utils;

import com.alibaba.fastjson.JSONObject;
import com.example.opct_notice.notice.dto.NoticeReqDTO;
import com.example.opct_notice.notice.dto.NoticeRespDTO;

/**
 * @Author Administrator
 * @Date 2021/12/19 17:37
 */
public class NoticeUtil {
    public static void unserialzeNoticeReq(NoticeReqDTO request, Class bodyClazz) {
//        request.setBody(JSONObject.parseObject(request.getBody().toString(), bodyClazz));
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(request.getBody());
        request.setBody(JSONObject.toJavaObject(jsonObject, bodyClazz));
    }

    public static void unserialzeNoticeResp(NoticeRespDTO response, Class bodyClazz) {
//        response.setBody(JSONObject.parseObject(response.getBody().toString(), bodyClazz));
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(response.getBody());
        response.setBody(JSONObject.toJavaObject(jsonObject, bodyClazz));
    }
}
