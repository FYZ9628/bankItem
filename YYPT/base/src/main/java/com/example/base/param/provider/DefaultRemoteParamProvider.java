package com.example.base.param.provider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.base.param.ParamBean;
import com.example.base.param.config.ParamConfig;
import com.example.base.param.entity.GenericRespDTO;
import com.example.base.param.entity.NullParamBean;
import com.example.base.param.remote.dto.RemoteParamQueryReqDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class DefaultRemoteParamProvider<T extends ParamBean> implements ParamProviderService<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRemoteParamProvider.class);
    //OPCT中的服务码
    private static final String REMOTE_PARAM_SERVICE_CODE = "remoteParamLoadService";
    private ParamConfig config;

    public DefaultRemoteParamProvider(ParamConfig config) {
        this.config = config;
    }

    @Override
    public List<T> list() {
        GenericRespDTO resp = null;
        try {
            resp = loadRemoteParam(null);
        } catch (Exception e) {
            LOGGER.error("loadRemoteParam {} fail!", config.getParamName(),e);
            return null;
        }
        JSONArray jsonArray = (JSONArray) resp.get("listOfParamBean");
        if (jsonArray == null) {
            LOGGER.info("loadRemoteParam {} but value is null!", config.getParamName());
            LOGGER.warn("运营中心远程参数查询，参数未找到，参数代码为：{}，key为：{}", config.getParamName(), null);
            return new ArrayList<>();
        } else {
            LOGGER.info("loadRemoteParam {} value {}!", config.getParamName(), jsonArray.subList(0, jsonArray.size()>5?5:jsonArray.size()));
            return jsonArray.toJavaList((Class<T>) config.getParamType());
        }
    }

    @Override
    public T getByKey(Object key) {
        GenericRespDTO resp = null;
        try {
            resp = loadRemoteParam(null);
        } catch (Exception e) {
            return null;
        }
        JSONObject jsonObject = (JSONObject) resp.get("paramBean");
        LOGGER.info("loadRemoteParam {} value {}!", config.getParamName(), key);
        if (jsonObject == null) {
            LOGGER.warn("运营中心远程参数查询，参数未找到，参数代码为：{}，key为：{}", config.getParamName(), key);
            return (T) new NullParamBean();
        } else {
            return JSONObject.parseObject(jsonObject.toJSONString(), (Class<T>) config.getParamType());
        }
    }

    private GenericRespDTO loadRemoteParam(Object key) throws Exception {
        String paramName = config.getParamName();
        RemoteParamQueryReqDTO remoteParamQueryReqDTO = new RemoteParamQueryReqDTO();
//        remoteParamQueryReqDTO.setHead(new BaseHead());
//        remoteParamQueryReqDTO.getHead().setServcCode(REMOTE_PARAM_SERVICE_CODE);
        remoteParamQueryReqDTO.setParamName(paramName);
        if (!StringUtils.isEmpty(key)) {
            remoteParamQueryReqDTO.setKey(key.toString());
        }
        GenericRespDTO respDTO = null;
        try {
//            respDTO = RpcUtil.execute(remoteParamQueryReqDTO, GenericRespDTO.class);
//            if (respDTO == null || respDTO.getHead() == null || !"000000".equals(respDTO.getHead().getRetCode())) {
//                throw new Exception("加载 param=【" + paramName + "】，key=【" + key + "】时调用远程服务【remoteParamLoadService】异常！");
//
//            }
        } catch (Exception e) {
            LOGGER.error("loadRemoteParam {} fail, resp = {}!", config.getParamName(), respDTO, e);
            throw new Exception("加载远程参数异常",e);
        }
        return respDTO;
    }
}
