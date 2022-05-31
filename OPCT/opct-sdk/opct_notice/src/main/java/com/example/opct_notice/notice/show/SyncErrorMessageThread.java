package com.example.opct_notice.notice.show;

import com.example.base.errormessage.entity.ErrorMessageDO;
import com.example.base.param.ParamCenterService;
import com.example.base.utils.JudgeUtils;
import com.example.opct_notice.base.errormessage.service.ErrorMessageService;
import com.example.opct_notice.notice.constant.AppIdConstant;
import com.example.opct_notice.notice.constant.NoticeConst;
import com.example.opct_notice.notice.dto.NoticeReqDTO;
import com.example.opct_notice.notice.dto.NoticeRespDTO;
import com.example.opct_notice.notice.entity.ErrorMessageReqBody;
import com.example.opct_notice.notice.enums.NoticeServiceEnum;
import com.example.opct_notice.notice.service.impl.AbstractGeneralNoticeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class SyncErrorMessageThread extends AbstractGeneralNoticeService implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SyncErrorMessageThread.class);

    @Value("${app.id}")
    private String appId;

    @Autowired
    private ErrorMessageService errorMessageService;
    @Autowired
    private ParamCenterService paramCenterService;

    @Override
    public void run() {
        try {
            String syncFlag = NoticeConst.ERROR_CODE_SYNC_FLAG;
            int pageNum = 1;
            PageHelper.startPage(pageNum, 100);
            if (JudgeUtils.notEquals(appId, AppIdConstant.APPID_OPCT)) {
                //关闭反向同步
                return;
            }
            List<ErrorMessageDO> errorMessageDOList = errorMessageService.listBySyncFlag(syncFlag);
            PageInfo pageInfo = new PageInfo(errorMessageDOList);
            int pages = pageInfo.getPages();
            while (JudgeUtils.isNotNull(errorMessageDOList) && pageNum <= pages) {
                NoticeReqDTO request = new NoticeReqDTO();
                ErrorMessageReqBody reqBody = new ErrorMessageReqBody();
                reqBody.setOperateType(NoticeConst.OPERATE_TYPE_CREATE);
                reqBody.setDoList(errorMessageDOList);
                request.setBody(reqBody);
                //从运营中心同步到其他中心
                if (JudgeUtils.equals(appId, AppIdConstant.APPID_OPCT)) {
                    for (ErrorMessageDO errorMessageDO : errorMessageDOList) {
                        errorMessageDO.setSyncFlag(syncFlag);
                    }
                    request.setNoticeServiceEnum(NoticeServiceEnum.ERROR_MSG_OPCT_SEND);
                } else {
                    //从其他中心同步到运营中心
                    request.setNoticeServiceEnum(NoticeServiceEnum.ERROR_MSG_OTHERS_SEND);
                }
                syncChroService(errorMessageDOList, request);
                PageHelper.startPage(++pageNum, 100, false);
                errorMessageDOList = errorMessageService.listBySyncFlag(syncFlag);
                if (pages > 0) {
                    //更新缓存
//                    paramCenterService.updateNodeInfo(ErrorMessageDO.PARAM_NAME, NoticeConst.MAIN_NODE);
                }
            }
        } catch (Exception e) {
            LOGGER.error("同步错误信息码失败，错误信息{}", e);
        }
    }

    @Transactional
    public void syncChroService(List<ErrorMessageDO> errorMessageDOList, NoticeReqDTO request) {
        try {
            NoticeRespDTO<Boolean> respDTO = this.doSendNotice(request);
            if (JudgeUtils.isNotNull(respDTO) && JudgeUtils.isNotNull(respDTO.getBody())) {
                if (respDTO.getBody()) {
//                    TransactionStatus status = DBUtil.benginTx(false);
                    try {
                        for (ErrorMessageDO errorMessageDO : errorMessageDOList) {
                            errorMessageDO.setSyncFlag(NoticeConst.ERROR_CODE_SYNC_FLAG);
                            errorMessageService.update(errorMessageDO);
                        }
//                        DBUtil.rollback(status);
                    } catch (Exception e) {
//                        DBUtil.rollback(status);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("同步错误信息码失败，错误信息{}", e);
        }
    }
}
