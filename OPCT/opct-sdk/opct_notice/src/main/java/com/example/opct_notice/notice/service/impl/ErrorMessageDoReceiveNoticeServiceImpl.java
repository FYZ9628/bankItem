package com.example.opct_notice.notice.service.impl;

import com.example.base.errormessage.entity.ErrorMessageDO;
import com.example.base.utils.JudgeUtils;
import com.example.opct_notice.base.errormessage.service.ErrorMessageService;
import com.example.opct_notice.notice.constant.AppIdConstant;
import com.example.opct_notice.notice.constant.NoticeConst;
import com.example.opct_notice.notice.dto.NoticeReqDTO;
import com.example.opct_notice.notice.dto.NoticeRespDTO;
import com.example.opct_notice.notice.entity.ErrorMessageReqBody;
import com.example.opct_notice.notice.service.DoReceiveNoticeService;
import com.example.opct_notice.notice.utils.NoticeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

/**
 * 错误信息码同步-处理通知服务
 */
@Component(value = NoticeConst.ERROR_MSG_DO_RECEIVE_NOTICE)
public class ErrorMessageDoReceiveNoticeServiceImpl implements DoReceiveNoticeService<ErrorMessageReqBody, Boolean> {
    @Autowired
    private ErrorMessageService errorMessageService;
    @Value("${app.id}")
    private String appId;

    @Override
    public NoticeRespDTO<Boolean> doReceiveNotice(NoticeReqDTO<ErrorMessageReqBody> request) throws Exception {
        NoticeRespDTO<Boolean> response = new NoticeRespDTO<Boolean>();
        boolean flag = false;
        if (JudgeUtils.isNotNull(request) && JudgeUtils.isNotNull(request.getBody())) {
            NoticeUtil.unserialzeNoticeReq(request, ErrorMessageReqBody.class);
            ErrorMessageReqBody errorMessageReqBody = request.getBody();
            if (JudgeUtils.isNotNull(errorMessageReqBody) && JudgeUtils.isNotNull(errorMessageReqBody.getDoList())) {
                flag = handle(errorMessageReqBody);
            }
            response.setBody(flag);
        }

        return response;
    }

    @Transactional
    public boolean handle(ErrorMessageReqBody errorMessageReqBody) {
        boolean flag = false;
        if (JudgeUtils.equals(appId, AppIdConstant.APPID_OPCT)) {
            return true;
        }

//        TransactionStatus status = DBUtil.beginTx(false);
        try {
            for (ErrorMessageDO errorMessageDO : errorMessageReqBody.getDoList()) {
                if (JudgeUtils.equals(NoticeConst.OPERATE_TYPE_CREATE, errorMessageReqBody.getOperateType())) {
                    ErrorMessageDO result = errorMessageService.selectByKey(errorMessageDO.getErrorInfoCode());
                    if (JudgeUtils.isNull(result)) {
                        errorMessageService.create(errorMessageDO);
                    } else {
                        errorMessageService.update(errorMessageDO);
                    }
                } else if (JudgeUtils.equals(NoticeConst.OPERATE_TYPE_UPDATE, errorMessageReqBody.getOperateType())) {
                    errorMessageService.update(errorMessageDO);
                } else if (JudgeUtils.equals(NoticeConst.OPERATE_TYPE_DELETE, errorMessageReqBody.getOperateType())) {
                    errorMessageService.delete(errorMessageDO.getErrorInfoCode());
                }
            }
            flag = true;
//            DBUtil.commit(status);
        } catch (Exception e) {
            flag = false;
//            DBUtil.rollback(status);
        }

        return flag;
    }
}
