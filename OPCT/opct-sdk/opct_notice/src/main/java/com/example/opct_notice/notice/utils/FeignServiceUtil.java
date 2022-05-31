package com.example.opct_notice.notice.utils;

import com.example.opct_notice.notice.constant.AppIdConstant;
import com.example.opct_notice.notice.feignclient.BlctFeignService;
import com.example.opct_notice.notice.feignclient.FeignService;
import com.example.opct_notice.notice.feignclient.OpctFeignService;
import com.example.opct_notice.notice.feignclient.SvctFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FeignServiceUtil {

    @Autowired
    private List<FeignService> listOfFeignService;
//    @Autowired
    private Map<String, FeignService> feignServiceMap = new HashMap<>();;

    @Value("${app.id}")
    private String appId;

    public FeignService getCurrentFeignService() {
        if (feignServiceMap != null && feignServiceMap.size() != 0) {
            if (feignServiceMap.get(appId) != null) {
                return feignServiceMap.get(appId);
            }
        }
        return null;
    }

    public FeignService getFeignServiceByAppId(String appIdStr) {
        if (feignServiceMap != null && feignServiceMap.size() != 0) {
            if (feignServiceMap.get(appIdStr) != null) {
                return feignServiceMap.get(appIdStr);
            }
        }
        return null;
    }

    @PostConstruct
    private void initFeignServiceData() {
        for (int i = 0; i < listOfFeignService.size(); i++) {
            if (listOfFeignService.get(i) instanceof OpctFeignService) {
                feignServiceMap.put(AppIdConstant.APPID_OPCT, listOfFeignService.get(i));
            }
            if (listOfFeignService.get(i) instanceof SvctFeignService) {
                feignServiceMap.put(AppIdConstant.APPID_SVCT, listOfFeignService.get(i));
            }
            if (listOfFeignService.get(i) instanceof BlctFeignService) {
                feignServiceMap.put(AppIdConstant.APPID_BLCT, listOfFeignService.get(i));
            }
        }
    }
}
