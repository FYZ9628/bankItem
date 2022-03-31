package com.example.base.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @description 判断工具类
 *
 * @author Administrator
 * @date 2021/7/23 1:19
 */
public class JudgeUtils {
    private static final Logger logger = LoggerFactory.getLogger(JudgeUtils.class);

    /**
     * 判断字符串是否为null或者blank
     *
     * @param t
     * @return
     */
    public static boolean isNull(String t) {
        return StringUtils.isEmpty(t);
    }

    public static <T> boolean isNull(T t) {
        return null == t;
    }

    public static boolean isNull(List t) {
        return CollectionUtils.isEmpty(t);
    }

    public static boolean isNull(Map t) {
        return CollectionUtils.isEmpty(t);
    }

    public static boolean isNull(BigDecimal t) {
        return null == t;
    }

    public static boolean isNull(Integer t) {
        return null == t;
    }

    /**
     * 非空判断
     *
     * @param t
     * @return
     */
    public static boolean isNotNull(String t) {
        return !isNull(t);
    }

    public static <T> boolean isNotNull(T t) {
        return !isNull(t);
    }

    public static boolean isNotNull(List t) {
        return !isNull(t);
    }

    public static boolean isNotNull(Map t) {
        return !isNull(t);
    }

    public static <T> boolean isNotNull(BigDecimal t) {
        return !isNull(t);
    }

    public static <T> boolean isNotNull(Integer t) {
        return !isNull(t);
    }

    /**
     * 泛型数组判空
     * @param array
     * @param <T>
     * @return
     */
    @SafeVarargs
    public static <T> boolean isNullAny(T... array) {
        if (null == array) {
            return true;
        }

        for (T t : array) {
            if (null == t) {
                return true;
            }
        }
        return false;
    }

    public static <T extends Comparable<T>> boolean equals(T t1, T t2) {
        if (t1 == t2) {
            return true;
        }
        if (t1 == null || t2 == null) {
            return false;
        }
        return t1.compareTo(t2) == 0;
    }

    public static <T extends Comparable<T>> boolean notEquals(T t1, T t2) {
        return !equals(t1, t2);
    }

    public static boolean equals(String str1, String str2) {
        return StringUtils.equals(str1, str2);
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        return StringUtils.equalsIgnoreCase(str1, str2);
    }

    public static boolean notEquals(String str1, String str2) {
        return !equals(str1, str2);
    }

    public static boolean equalsAny(String str1, String... strs) {
        if (null == strs && null == str1) {
            return true;
        }
        if (null == strs) {
            return false;
        }
        boolean f = false;
        for (String s : strs) {
            if (equals(str1, s)) {
                f = true;
                break;
            }
        }
        return f;
    }

    /**
     * 参数一 大于 参数二 则返回true
     * @param t1
     * @param t2
     * @param <T>
     * @return
     */
    public static <T extends Comparable<T>> boolean isGT(T t1, T t2) {
        if (isNull(t1) || isNull(t2)) {
            return false;
        }
        if (t1.compareTo(t2) > 0) {
            return true;
        }
        return false;
    }

    /**
     * 参数一 大于等于 参数二 则返回true
     * @param t1
     * @param t2
     * @param <T>
     * @return
     */
    public static <T extends Comparable<T>> boolean isGE(T t1, T t2) {
        if (isNull(t1) || isNull(t2)) {
            return false;
        }
        if (t1.compareTo(t2) >= 0) {
            return true;
        }
        return false;
    }

    /**
     * 参数一 小于 参数二 则返回true
     * @param t1
     * @param t2
     * @param <T>
     * @return
     */
    public static <T extends Comparable<T>> boolean isLT(T t1, T t2) {
        if (isNull(t1) || isNull(t2)) {
            return false;
        }
        if (t1.compareTo(t2) < 0) {
            return true;
        }
        return false;
    }

    /**
     * 参数一 小于等于 参数二 则返回true
     * @param t1
     * @param t2
     * @param <T>
     * @return
     */
    public static <T extends Comparable<T>> boolean isLE(T t1, T t2) {
        if (isNull(t1) || isNull(t2)) {
            return false;
        }
        if (t1.compareTo(t2) <= 0) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String str) {
        return StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        return StringUtils.isBlank(str);
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isBlankAll(String... args) {
        if (null == args) {
            return true;
        }
        boolean f = true;
        for (String s : args) {
            if (isBlank(s)) {
                f = false;
                break;
            }
        }
        return f;
    }

    public static boolean isNotBlankAll(String... args) {
        if (null == args) {
            return false;
        }
        boolean f = true;
        for (String s : args) {
            if (isBlank(s)) {
                f = false;
                break;
            }
        }
        return f;
    }

    public static boolean isBlankAny(String... args) {
        if (null == args) {
            return true;
        }
        boolean f = false;
        for (String s : args) {
            if (isBlank(s)) {
                f = true;
                break;
            }
        }
        return f;
    }

    public static boolean isEmpty(Collection<?> c) {
        return c == null || c.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> c) {
        return !isEmpty(c);
    }

    public static boolean isEmpty(Map<?, ?> m) {
        return m == null || m.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> m) {
        return !isEmpty(m);
    }

    public static boolean isSuccess(String msgCd) {
        if (StringUtils.isEmpty(msgCd)) {
            //报错
        }
        int len = msgCd.length();
        return StringUtils.equals(msgCd,"success");
    }

    public static boolean isNotSuccess(String msgCd) {
        return !isSuccess(msgCd);
    }

    public static <T> boolean isEmpty(T[] ts) {
        if (null == ts) {
            return true;
        }
        if (ts.length <= 0) {
            return true;
        }
        return false;
    }

    public static <T> boolean isNotEmpty(T[] ts) {
       return !isEmpty(ts);
    }

    public static boolean contain(String[] args, String s) {
        if(isEmpty(args)){
            return false;
        }
        boolean f = false;
        for (String a : args) {
            if (equals(a, s)) {
                f = true;
                break;
            }
        }
        return f;
    }

    /**
     * 第一个字符串是否包含第二个字符串
     * @param firstStr 第一个字符串
     * @param second    第二个字符串
     * @return 是否包含
     */
    public static boolean contains(String firstStr, String second) {
        if (isEmpty(firstStr) || isEmpty(second)) {
            return false;
        }
        boolean f = false;
        if (firstStr.contains(second)) {
            f = true;
        }
        return f;
    }

    /**
     * 是否包含MqUrl
     *
     * @param firstStr  第一个字符串
     * @param second    第二个字符串
     * @return 是否包含
     */
    private static boolean containsMqUrl(String firstStr, String second) {
        if (isEmpty(firstStr) || isEmpty(second)) {
            return false;
        }
        boolean f = false;
        if (firstStr.contains(second)) {
            f = true;
        }
        return f;
    }

    /**
     * 是否没有包含MqUrl
     *
     * @param firstStr  第一个字符串
     * @param second    第二个字符串
     * @return 是否包含
     */
    public static boolean notContainsMqUrl(String firstStr, String second) {
        return !containsMqUrl(firstStr, second);
    }

    public static boolean not(boolean flag) {
        return !flag;
    }

    public static boolean isTrue(Boolean flag, boolean defaultFlag) {
        if (null == flag) {
            return defaultFlag;
        }
        return flag;
    }

    public static boolean available(InputStream inputStream) {
        try {
            if (null != inputStream && inputStream.available() > 0) {
                return true;
            }
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error("Failed to during available input stream.", e);
            }
        }
        return false;
    }

    /**
     * 字符串中字符是否全为 s
     *
     * @param str
     * @param s
     * @return
     */
    public static boolean isAllChar(String str, char s) {
        byte[] strBytes = str.getBytes();
        for (int i = 0; i < strBytes.length; i++) {
            if (strBytes[i] != s) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串中是否全为数字
     *
     * @param str
     * @return
     */
    public static boolean isAllDigital(String str) {
        boolean isDigit = true;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch < '0' || ch > '9') {
                isDigit = false;
                break;
            }
        }
        return isDigit;
    }

    public static boolean isVersionUpdateErr(Throwable t) {
        Throwable cause = t;
        while (cause != null) {
//            if (cause instanceof VersionUpdateException) {
//                return true;
//            }
            cause = cause.getCause();
        }
        return false;
    }

    /**
     * 判断是否为 0
     *
     * @param t
     * @return
     */
    public static boolean isZero(Character t) {
        if (t.equals('0')) {
            return true;
        }
        return false;
    }

    public static boolean isZero(String t) {
        t = t.trim();
        if (isAllDigital(t)) {
            BigDecimal bigDecimal = new BigDecimal(t);
            if (0 == bigDecimal.intValue()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isZero(BigDecimal t) {
        if (0 == t.intValue()) {
            return true;
        }
        return false;
    }

    public static boolean isZero(short t) {
        if (0 == t) {
            return true;
        }
        return false;
    }

    public static boolean isZero(Integer t) {
        if (0 == t) {
            return true;
        }
        return false;
    }

    public static boolean isZero(Long t) {
        if (0 == t) {
            return true;
        }
        return false;
    }

    public static boolean isZero(Float t) {
        if (0 == t) {
            return true;
        }
        return false;
    }

    public static boolean isZero(Double t) {
        if (0 == t) {
            return true;
        }
        return false;
    }

}
