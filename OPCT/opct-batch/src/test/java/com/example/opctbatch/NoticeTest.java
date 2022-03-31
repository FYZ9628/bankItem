package com.example.opctbatch;

import com.alibaba.fastjson.JSONObject;
import com.example.base.utils.JudgeUtils;
import com.example.opct_base.sequence.entity.SeqNoDO;
import com.example.opct_notice.base.sequence.service.SeqNoService;
import com.example.opct_notice.notice.entity.QuerySeqNoReqBody;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class NoticeTest {
//    @Autowired(required = true)
    @Resource
    private SeqNoService seqNoService;

    @Test
    public void testSeqNo() {
        String appId = "OPCT";
        String seqType = "FYZ";
        System.out.println(appId);
        QuerySeqNoReqBody reqBody = new QuerySeqNoReqBody();
        reqBody.setCurrentPage(0);
        reqBody.setSeqType(seqType);
        reqBody.setAppId(appId);
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSON(reqBody).toString());


        List<SeqNoDO> seqNoDOList = seqNoService.getAllSeqNo(appId, seqType);
        if (JudgeUtils.isNotNull(seqNoDOList)) {
            System.out.println(seqNoDOList.toString());
        }
    }
}
