package com.example.opct_notice.base.sequence.mapper;

import com.example.opct_base.sequence.entity.SeqNoDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SequenceNoMapper {
    /**
     * 根据顺序号类型查询
     * @param seqNoDO
     * @return
     */
    List<SeqNoDO> listBySeqType(SeqNoDO seqNoDO);
}
