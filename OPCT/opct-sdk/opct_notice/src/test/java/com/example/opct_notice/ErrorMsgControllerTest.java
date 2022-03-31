package com.example.opct_notice;

import com.example.base.errormessage.entity.ErrorMessageDO;
import com.example.base.errormessage.service.ErrorParamService;
import com.example.opct_notice.base.errormessage.service.ErrorMessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootTest
public class ErrorMsgControllerTest {
    @Autowired
    private ErrorMessageService errorMessageService;

    @Test
    public void testErrorMsg() {
        List<ErrorMessageDO> list = errorMessageService.list();
        for (ErrorMessageDO messageDO : list) {
            System.out.println(messageDO);
        }

        ErrorMessageDO messageDO2 = (ErrorMessageDO) errorMessageService.selectByKey("OPCT1001");
        System.out.println(messageDO2.toString());

    }
}
