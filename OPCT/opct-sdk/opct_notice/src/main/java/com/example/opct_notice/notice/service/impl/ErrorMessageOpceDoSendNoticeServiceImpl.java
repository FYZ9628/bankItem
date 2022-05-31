package com.example.opct_notice.notice.service.impl;

import com.example.base.errormessage.entity.ErrorMessageDO;
import com.example.base.utils.JudgeUtils;
import com.example.opct_notice.notice.constant.AppIdConstant;
import com.example.opct_notice.notice.constant.NoticeConst;
import com.example.opct_notice.notice.dto.NoticeReqDTO;
import com.example.opct_notice.notice.dto.NoticeRespDTO;
import com.example.opct_notice.notice.entity.ErrorMessageReqBody;
import com.example.opct_notice.notice.enums.NoticeAppIdEnum;
import com.example.opct_notice.notice.service.DoSendNoticeService;
import com.example.opct_notice.notice.utils.FeignServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 错误信息码同步-运营中心发送通知服务
 */
@Component(value = NoticeConst.ERROR_MSG_OPCT_DO_SEND_NOTICE)
public class ErrorMessageOpceDoSendNoticeServiceImpl implements DoSendNoticeService<ErrorMessageReqBody, Boolean> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorMessageOpceDoSendNoticeServiceImpl.class);

    @Autowired
    private FeignServiceUtil feignServiceUtil;

    /**
     * 发送通知
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public NoticeRespDTO<Boolean> doSendNotice(NoticeReqDTO<ErrorMessageReqBody> request) throws Exception {
        NoticeRespDTO<Boolean> response = new NoticeRespDTO<Boolean>();
        boolean flag = true;
        StringBuilder builder = new StringBuilder();

        for (NoticeAppIdEnum notice : NoticeAppIdEnum.values()) {
            if (JudgeUtils.isNull(notice.getServCode()) || JudgeUtils.equals(notice.getAppId(), AppIdConstant.APPID_OPCT)) {
                continue;
            }
            int i = 0;
//            request.setHead(new BaseHead());
//            request.getHead().setServcCode(notice, NoticeRespDTO.class);
            do {
//                response = RpcUtil.execute(request, NoticeRespDTO.class);
                response = feignServiceUtil.getFeignServiceByAppId(notice.getAppId()).receiveNotice(request);
                i++;
            } while ((JudgeUtils.isNull(response) || JudgeUtils.isNull(response.getBody()) || !response.getBody()) && i < 5);

            if (JudgeUtils.isNotNull(response) && JudgeUtils.isNotNull(response.getBody()) && !response.getBody()) {
                flag = false;
                builder.append(notice.getAppId()).append(NoticeConst.COMMA);
            }
        }
        if (!flag) {
            String msg = builder.toString();
            msg = msg.substring(0, msg.length()-1);
            List<ErrorMessageDO> errorMessageDOList = request.getBody().getDoList();
            if (JudgeUtils.isNotNull(errorMessageDOList) && errorMessageDOList.size() > 0) {
                LOGGER.error("错误信息码：{}-{}[{}] 更新失败 {}", errorMessageDOList.get(0).getErrorInfoCode(),
                        errorMessageDOList.get(errorMessageDOList.size()-1).getErrorInfoCode(),msg);
            }

            response = new NoticeRespDTO<Boolean>();
            response.setBody(false);
        }

        return response;
    }
}
