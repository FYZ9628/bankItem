package com.example.opctonline.sequence;

import com.example.opct_base.sequence.entity.SeqNoDO;
import com.example.opct_base.sequence.service.SequenceNoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SeqNoController {

    @Autowired
    private SequenceNoService sequenceNoService;


    @RequestMapping("testSeqNo")
    public void testSeqNo() {
        List<SeqNoDO> doList = sequenceNoService.listBySeqType("FYZ");
        for (SeqNoDO seqNoDO : doList) {
            System.out.println(seqNoDO.getSeqDesc());
        }
        System.out.println(doList.toString());
    }
}
