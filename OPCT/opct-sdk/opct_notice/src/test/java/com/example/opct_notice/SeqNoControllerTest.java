package com.example.opct_notice;

import com.example.opct_base.sequence.entity.SeqNoDO;
import com.example.opct_base.sequence.service.SequenceNoService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootTest
public class SeqNoControllerTest {

    @Autowired
    private SequenceNoService sequenceNoService;

    @Test
    public void listBySeqType() {
        List<SeqNoDO> doList = sequenceNoService.listBySeqType("FYZ");
        for (SeqNoDO seqNoDO : doList) {
            System.out.println(seqNoDO.getSeqDesc());
        }
        System.out.println(doList.toString());
    }
}
