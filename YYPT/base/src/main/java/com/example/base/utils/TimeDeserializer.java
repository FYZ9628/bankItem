package com.example.base.utils;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;
import java.sql.Time;

/**
 * time反序列化
 */
public class TimeDeserializer implements ObjectDeserializer {
    private static final int LEN_8 = 8;
    private static final int LEN_6 = 6;
    private static final String TOKEN = ":";

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        String val = parser.lexer.stringVal();
        if (val != null) {
            if (val.length() == LEN_8 && val.contains(TOKEN)) {
                return (T) Time.valueOf(val);
            }
            if (val.length() == LEN_6) {
                val = val.substring(0, 2) + TOKEN + val.substring(2, 4) + TOKEN + val.substring(4, 6);
                return (T) Time.valueOf(val);
            }
        }
        return null;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
