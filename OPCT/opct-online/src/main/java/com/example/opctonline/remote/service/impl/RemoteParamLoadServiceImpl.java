package com.example.opctonline.remote.service.impl;

import com.example.base.param.ParamBean;
import com.example.base.param.annotation.ParamProvider;
import com.example.base.param.remote.dto.RemoteParamQueryReqDTO;
import com.example.base.param.remote.dto.RemoteParamQueryRespDTO;
import com.example.base.utils.JudgeUtils;
import com.example.opct_notice.notice.feignclient.FeignService;
import com.example.opctonline.remote.service.AbstractRemoteParamService;
import com.example.opctonline.remote.service.RemoteParamLoadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RemoteParamLoadServiceImpl<T extends ParamBean> implements RemoteParamLoadService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteParamLoadServiceImpl.class);

    @Autowired
    private List<AbstractRemoteParamService<T>> listOfRemoteParamService;

    @Autowired
    private List<FeignService> listOfFeignService;

    @Autowired
    private Map<String, FeignService> feignServiceMap;

    private Map<String, AbstractRemoteParamService<T>> serviceMap = new HashMap<>();

    @RequestMapping("loadRemoteParam")
    public RemoteParamQueryRespDTO<T> loadRemoteParam(RemoteParamQueryReqDTO reqDTO) throws Exception {
        String paramName = reqDTO.getParamName();
        RemoteParamQueryRespDTO<T> remoteParamQueryRespDTO = new RemoteParamQueryRespDTO<>();
        AbstractRemoteParamService<T> remoteParamService = serviceMap.get(paramName);
        if (remoteParamService == null) {
            LOGGER.error("there is no param provider of {}", paramName);
            throw new Exception("can't find remoteParamService of " + paramName);
        }
        if (JudgeUtils.isNotBlank(reqDTO.getKey())) {
            T paramBean = remoteParamService.getParamByKey(reqDTO.getKey());
            if (JudgeUtils.isNull(paramBean)) {
                LOGGER.warn("运营中心远程参数查询，参数未找到，参数代码为：{}，key为：{}", paramName, reqDTO.getKey());
            }
            remoteParamQueryRespDTO.setParamBean(paramBean);
        } else {
            List<T> allParam = remoteParamService.getAllParam();
            if (JudgeUtils.isNull(allParam)) {
                LOGGER.warn("运营中心远程参数查询，参数未找到，参数代码为：{}，key为：{}", paramName, reqDTO.getKey());
            }
            remoteParamQueryRespDTO.setListOfParamBean(allParam);
        }
//        BaseHead head = reqDTO.getHead();
//        head.setRetCode("000000");
//        remoteParamQueryRespDTO.setHead(head);

        return remoteParamQueryRespDTO;
    }

    @PostConstruct
    private void initData() {
        for (AbstractRemoteParamService<T> service : listOfRemoteParamService) {
            ParamProvider paramProvider = AopUtils.getTargetClass(service).getAnnotation(ParamProvider.class);
            if (paramProvider != null) {
                serviceMap.put(paramProvider.paramName(), service);
                LOGGER.info("load param provider {} of {}", service.getClass().getName(), paramProvider.paramName());
            }
        }
    }
}
