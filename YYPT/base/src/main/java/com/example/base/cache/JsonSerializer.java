package com.example.base.cache;

import cn.hutool.core.lang.TypeReference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

public class JsonSerializer<T> implements Serializer<T, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonSerializer.class);

    @Override
    public String serialize(T data) {
        return JSON.toJSONString(data, SerializerFeature.WriteClassName);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DR> DR deSerialize(String data) {
        try {
            if (StringUtils.isEmpty(data)) {
                return null;
            }
            Type type = new TypeReference<DR>() {
            }.getType();
            ParserConfig.getGlobalInstance().setSafeMode(false);
            return JSON.parseObject(data, type, Feature.SupportAutoType);
        } catch (Exception e) {
            LOGGER.error("fail to deSerialize data: {}", data, e);
            return null;
        }
    }
}
