package com.example.base.param;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/**
 * 参数中心服务
 */
public interface ParamCenterService {
    /**
     * 加载所有参数
     * @param paramName
     * @param predicate
     * @param <T>
     * @return
     */
    <T extends ParamBean>List<T> list(String paramName, Predicate<T> predicate) throws Exception;

    /**
     * 加载所有参数
     * @param paramName
     * @param <T>
     * @return
     */
    <T extends ParamBean>List<T> list(String paramName) throws Exception;

    /**
     * 加载所有参数，此方法优于list，正常情况
     * @param paramName
     * @param <T>
     * @return
     */
    <T extends ParamBean> Collection<T> collection(String paramName) throws Exception;

    /**
     * 根据key查找
     * @param paramName
     * @param key
     * @param <T>
     * @return
     */
    <T extends ParamBean>T getByKey(String paramName, Object key) throws Exception;

    /**
     * 根据key查找，不使用缓存
     * @param paramName
     * @param key
     * @param <T>
     * @return
     */
    <T extends ParamBean>T getByKeyFromRemote(String paramName, Object key);

    /**
     * 获取缓存中唯一一条数据
     * @param paramName
     * @param <T>
     * @return
     */
    <T extends ParamBean>T getOnlyParam(String paramName) throws Exception;

    /**
     * 同步参数
     * @param paramName
     */
    void syncNodeInfo(String paramName);

    /**
     * 更新节点
     * @param paramName
     * @param nodeName
     */
    void updateNodeInfo(String paramName, String nodeName);

    Class<?> getParamType(String paramName);
}
