package com.example.opct_notice.notice.entity;

import com.example.opct_base.sequence.entity.SeqNoDO;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询顺序表信息响应体
 */
public class QuerySeqNoRespBody {
    /**
     * 是否为最后一页标志
     */
    private boolean lastPageFlag = true;
    /**
     * 当前页
     */
    private Integer currentPage = 0;
    /**
     * 顺序号信息数组
     */
    private List<SeqNoDO> seqNoDOList = new ArrayList<>();

    public boolean isLastPageFlag() {
        return lastPageFlag;
    }

    public void setLastPageFlag(boolean lastPageFlag) {
        this.lastPageFlag = lastPageFlag;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<SeqNoDO> getSeqNoDOList() {
        return seqNoDOList;
    }

    public void setSeqNoDOList(List<SeqNoDO> seqNoDOList) {
        this.seqNoDOList = seqNoDOList;
    }
}
