package com.example.opctonline.branch.application.service.impl;

import com.example.base.param.annotation.ParamProvider;
import com.example.base.param.remote.BranchInfo;
import com.example.opctonline.branch.application.dto.BranchInfoRespDTO;
import com.example.opctonline.branch.application.service.BranchAppService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ParamProvider(paramName = BranchInfo.PARAM_NAME)
@RestController
public class BranchAppServiceImpl implements BranchAppService {
    @Override
    public BranchInfoRespDTO getParamByKey(String id) throws Exception {
        return null;
    }

    @Override
    public List<BranchInfoRespDTO> getAllParam() throws Exception {
        return null;
    }
}
