package com.example.base.param.entity;

import java.util.Map;

public class GenericRespDTO {
    private transient Map<String, Object> originalRespJsonData$;

    public GenericRespDTO(Map<String, Object> originalRespJsonData) {
        this.originalRespJsonData$ = originalRespJsonData;
    }

    public GenericRespDTO() {
    }

    public Object get(String key) {
        if (null == originalRespJsonData$) {
            return null;
        }
        return originalRespJsonData$.get(key);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
