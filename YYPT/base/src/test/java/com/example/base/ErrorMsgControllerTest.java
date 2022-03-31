package com.example.base;

import com.example.base.errormessage.entity.ErrorMessageDO;
import com.example.base.errormessage.service.ErrorParamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ErrorMsgControllerTest {
    @Autowired
    private ErrorParamService errorParamService;

    @Test
    public void testErrorMsg() {
        List<ErrorMessageDO> list = errorParamService.list();
        for (ErrorMessageDO messageDO : list) {
            System.out.println(messageDO);
        }

        ErrorMessageDO messageDO2 = (ErrorMessageDO) errorParamService.getByKey("OPCT1001");
        System.out.println(messageDO2.toString());

    }
}
