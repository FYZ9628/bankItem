package com.example.base.param.service.impl;

import com.example.base.cache.CacheData;
import com.example.base.cache.ICoreCache;
import com.example.base.param.CustomerCacheProvider;
import com.example.base.param.ParamBean;
import com.example.base.param.ParamBeanWrap;
import com.example.base.param.ParamCenterService;
import com.example.base.param.annotation.CoreBankParam;
import com.example.base.param.config.ParamConfig;
import com.example.base.param.entity.*;
import com.example.base.param.mapper.ParamNodeSyncMapper;
import com.example.base.param.provider.DefaultRemoteParamProvider;
import com.example.base.thread.RtpAsyncService;
import com.example.base.thread.RtpThreadFactory;
import com.example.base.utils.JudgeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component(value = "ParamCenterService")
public class ParamCenterServiceImpl implements ParamCenterService, ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParamCenterServiceImpl.class);
    private static final String REMOTE_PARAM_SERVICE_CODE = "svrRmtCacheParamMainNodeInfoObt";
    private static final String DEFAULT_PACKAGE = "com.cgb";
    private static final String BATCH_ROOT_PACKAGE = "cgb";
    private static final String MAIN_NODE = "MAIN_NODE";
    private static final long DELAY_LOAD = 1_000L;
    private static final long DELAY_CHECK = 60_000L;
    private static ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor(new RtpThreadFactory("paramCenter"));

    private ApplicationContext applicationContext;
    private Environment environment;

    public void setApplicationContext(ApplicationContext applicationContext) throws Exception {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取环境变量
     * @return
     */
    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * 获取配置属性值（配置文件）
     * @param name
     * @return
     */
    public String getProperty(String name) {
        return environment.getProperty(name);
    }

    /**
     * 获取配置属性值（配置文件，若无该配置，返回默认值
     * @param name
     * @param defaultValue
     * @return
     */
    public String getProperty(String name, String defaultValue) {
        return environment.getProperty(name, defaultValue);
    }


    /**
     * 缓存
     */
    @Autowired
    private ICoreCache coreCache;

    @Autowired
    private ParamNodeSyncMapper nodeSyncMapper;

    @Autowired
    private RtpAsyncService rtpAsyncService;

    private Map<String, ParamConfig> paramConfigs = new HashMap<>();

    @Deprecated
    @Override
    public <T extends ParamBean> List<T> list(String paramName) throws Exception {
        Optional<Map<String, T>> optionalList = loadData(paramName);
        List<T> result = new ArrayList<>();
        result.addAll(optionalList.orElse(new HashMap<>()).values());
        if (LOGGER.isDebugEnabled()) {
            int maxIndex =result.size();
            if (result.size() > 5) {
                maxIndex = 5;
            }
            LOGGER.debug("list param={} values={} ...", paramName, result.subList(0, maxIndex));
        }

        return result;
    }

    @Override
    public <T extends ParamBean> Collection<T> collection(String paramName) throws Exception {
        Optional<Map<String, T>> optionalList = loadData(paramName);
        Collection<T> result = optionalList.orElse(new HashMap<>()).values();
        if (LOGGER.isDebugEnabled()) {
            int maxIndex =result.size();
            if (result.size() > 5) {
                maxIndex = 5;
            }
            LOGGER.debug("list param={} values={} ...", paramName, new ArrayList<>(result).subList(0, maxIndex));
        }

        return result;
    }

    @Override
    public <T extends ParamBean> List<T> list(String paramName, Predicate<T> predicate) throws Exception {
        Collection<T> list = collection(paramName);
        List<T> result = list.parallelStream().filter(predicate).collect(Collectors.toList());
        if (LOGGER.isDebugEnabled()) {
            int maxIndex =result.size();
            if (result.size() > 5) {
                maxIndex = 5;
            }
            LOGGER.debug("list param={} after filter values={} ...", paramName, result.subList(0, maxIndex));
        }

        return result;
    }

    @Override
    public <T extends ParamBean> T getByKey(String paramName, Object key) throws Exception {
        ParamConfig config = null;
        Optional<Map<String, T>> optionalList = null;
        T o = null;
        Object printKey = key;
        if ((config = paramConfigs.get(paramName)) != null) {
            if (config.isSensitiveKey()) {
//                printKey = DesensitizationUtil.around(String.valueOf(printKey), 3, 2);
            }
            optionalList = loadData(paramName);
            if (optionalList.isPresent()) {
                o = optionalList.get().get(key);
            } else {
                LOGGER.error("get param={} by key={} ,but after loadData there is no data!", paramName, printKey);
            }
            if (o == null && !config.isLoadAll()) {
                o = (T) config.getProviderService().getByKey(key);
                if (config.isCache()) {
                    if (o != null && !(o instanceof NullParamBean)) {
                        o = wrapParam(o);
                    } else {
                        if (config.getProviderService().getClass() != DefaultRemoteParamProvider.class) {
                            o = (T) new NullParamBean();
                        }
                    }
                    optionalList = loadData(paramName);
                    Map<String, T> mapValue = optionalList.orElse(new ConcurrentHashMap<>());
                    if (o != null) {
                        mapValue.put(String.valueOf(key), o);
                    }
                    saveToCache(paramName, mapValue);
                }
            }
            if (o instanceof NullParamBean) {
                o = null;
            }
        } else {
            LOGGER.error("get param={} by key={}, but not find it's config!", paramName, printKey);
//            throw new Exception("get param=" + paramName + " by key=" + ", but not find it's config!");
        }
        if (o == null) {
            LOGGER.error("get param={} by key={} ,but find null!", paramName, key);
            if (optionalList != null && optionalList.isPresent()) {
                LOGGER.error("get param={} by key={} ,but after loadData there is no data! loadData size={}",paramName,printKey, optionalList.get().size());
            }
        }
        LOGGER.debug("get param={} by key={}, value={}", paramName, printKey, o);
        return o;
    }

    @Override
    public <T extends ParamBean> T getByKeyFromRemote(String paramName, Object key) {
        ParamConfig config = null;
        T o = null;
        if ((config = paramConfigs.get(paramName)) != null) {
            o = (T) config.getProviderService().getByKey(key);
        }
        if (o instanceof NullParamBean) {
            o = null;
        }
        return o;
    }

    @Override
    public <T extends ParamBean> T getOnlyParam(String paramName) throws Exception {
        List<T> list = list(paramName);
        T o = list != null && list.size() > 0 ? list.get(0) : null;
        LOGGER.debug("getOnlyParam by param={} value={}", paramName,o);
        return o;
    }

    @Override
    public void syncNodeInfo(String paramName) {
        ParamConfig config = paramConfigs.get(paramName);
        if (config == null) {
            LOGGER.error("this param={} is not in local ParamCenter", paramName);
            return;
        }
        applicationContext.getBean(ParamCenterService.class).updateNodeInfo(paramName, MAIN_NODE);
    }

    /**
     * 加载数据
     *
     * @param paramName
     * @param <T>
     * @return
     * @throws Exception
     */
    private <T extends ParamBean> Optional<Map<String, T>> loadData(String paramName) throws Exception {

        ParamConfig config = paramConfigs.get(paramName);
        if (config == null) {
            LOGGER.error("loadData by paramName={} ,but not find it's config!", paramName);
            throw new Exception("loadData by paramName=" + paramName + " ,but not find it's config!");
        }

        Optional<Map<String, T>> paramInCache = Optional.ofNullable(null);
        //非缓存参数，直接获取
        if (config.isCache()) {
            Map<String, T> cacheData = loadFromCache(config);
            if (cacheData != null && cacheData.size() > 0) {
                paramInCache = Optional.of(cacheData);
            } else {
                LOGGER.info("{} cache size is 0! loadAll = {}", paramName, config.isLoadAll());
                //只有全load数据，才进行list操作，并缓存
                if (config.isLoadAll()) {
                    return tryToLoadData(paramName, config, paramInCache);
                }
            }
        } else {
            if (config.isLoadAll()) {
                List<T> data = config.getProviderService().list();
                Map<String, T> mapValue = conver2Map((List<T>) data, paramName);
                paramInCache = Optional.ofNullable(mapValue);
            }
        }
        return paramInCache;
    }

    /**
     * 尝试从DB加载数据
     *
     * @param paramName
     * @param config
     * @param paramInCache
     * @param <T>
     * @return
     */
    private <T extends ParamBean> Optional<Map<String, T>> tryToLoadData(String paramName, ParamConfig config, Optional<Map<String, T>> paramInCache) {
        //并发时，自旋等待次数，每次等待10ms
        int count = Integer.parseInt(environment.getProperty("spinTime", "200"));
        Map<String, T> cacheData = null;
        try {
            while (count-- > 0) {
                if (config.getThreadRunningLock()) {
                    List<T> data = config.getProviderService().list();
                    LOGGER.info("{} {} load data list is {}", paramName, config.getProviderService(), data == null ? 0 : data.size());
                    if (data != null && data.size() > 0) {
                        data = wrapParam(data);
                        Map<String, T> mapValue = conver2Map(data, paramName);
                        paramInCache = Optional.of(mapValue);
                    }
                    config.resetThreadRunning();
                    return paramInCache;
                }
                waitAndSleep(config);

                //等待10ms后，再尝试从缓存获取数据
                cacheData = loadFromCache(config);
                if (cacheData != null && cacheData.size() > 0) {
                    return Optional.of(cacheData);
                }
            }
            LOGGER.error("{} in cache is not ready!", paramName);
        } catch (Exception e) {
            LOGGER.error(config.getCacheKey() + "loadData exception", e);
            config.resetThreadRunning();
        }
        return paramInCache;
    }

    /**
     * 等待10ms
     *
     * @param config
     */
    private void waitAndSleep(ParamConfig config) {
        if (config.getThreadRunningState()) {
            try {
                TimeUnit.MICROSECONDS.sleep(10);
            } catch (InterruptedException e) {
                LOGGER.error("Thread {} Interrupted", Thread.currentThread().getName());
            }
        }
    }

    private <T extends ParamBean> Map<String, T> conver2Map(List<T> data, String paramName) throws Exception {
        Map<String, T> mapValue = new ConcurrentHashMap<>();
        if (data != null) {
            for (T v : data) {
                if (v != null && v.uniqueKey() != null) {
                    T previous = mapValue.put(v.uniqueKey(), v);
                    if (previous != null) {
                        throw new Exception("参数" + paramName + " 记录1 "+ previous + " 记录2" + v + " 存在相同联合主键：" + v.uniqueKey() + " , 请修改");
                    }
                }
            }
        }
        return mapValue;
    }

    private <T extends ParamBean> Map<String, T> loadFromCache(ParamConfig config) {
        return coreCache.get(config.getCacheKey(), config.getClazzOfDataKind());
    }

    /**
     * 记录缓存
     *
     * @param paramName
     * @param data
     * @param <T>
     */
    private <T extends ParamBean> void saveToCache(String paramName, Map<String, T> data) {
        LOGGER.info("save params to memoryCache for {}, values top 10 is {}!", paramName, data != null ? data.values().stream().limit(10).collect(Collectors.toList()) : null);

        ParamConfig config = paramConfigs.get(paramName);
        CacheData<?> cacheData = new CacheData<>(config.getCacheKey(), data, config.getClazzOfDataKind(), config.getValidTime());
        boolean needCahce = true;
        if (config.getCustomCacheProvider() != CustomerCacheProvider.DEFAULT_CACHE_PROVIDER) {
            CustomerCacheProvider customerCacheProvider = applicationContext.getBean(config.getCustomCacheProvider());
            long cacheTime = customerCacheProvider.getCacheTime(new ArrayList<>(data.values()));
            if (cacheTime > 0) {
                cacheData.setTimeout(cacheTime);
            } else {
                needCahce = false;
            }
        }
        if (needCahce) {
            coreCache.cache(cacheData);
            if (config.isLoadAll()) {
                //非一次性加载的，不用每次都更新时间，只在系统初始化以及，防止大数据量的按需查询导致更新节点时间太多
//                ParamCenterServiceImpl.this.updateNodeInfo(paramName, ToolUtil.getNodeName());
            }
        }
    }

    /**
     * 同步参数节点信息
     * 添加独立事务，用于避免事务被包裹时候，sql执行跟随上层事务回滚
     * @param paramName
     * @param nodeName
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void updateNodeInfo(String paramName, String nodeName) {
        try {
            updateNodeInfo(new ParamNodeBean(paramName, nodeName));
        } catch (Exception e) {
            LOGGER.error("update NodeInfo fail, ignore!", e);
        }
    }

    @Override
    public Class<?> getParamType(String paramName) {
        ParamConfig config;
        if ((config = paramConfigs.get(paramName)) != null) {
            return config.getParamType();
        }
        return null;
    }

    /**
     * 加载参数配置
     *
     * @param context
     */
    private void loadParamConfig(ApplicationContext context) throws Exception {
//        LOGGER.info("=================== load params Config =================");
//        AnnotationScanner scanner = new AnnotationScanner();
//        Set<BeanDefinition> beanDefins = scanner.scan(CoreBankParam.class, DEFAULT_PACKAGE, BATCH_ROOT_PACKAGE);
//
//        //获取MAIN 节点所有配置
//        List<ParamNodeBean> mainNodeParams = ParamCenterServiceImpl.this.nodeSyncMapper.queryMyNodeAndMainNode(null);
//        mainNodeParams = mainNodeParams.parallelStream().filter(i -> MAIN_NODE.equals(i.getNodeName())).collect(Collectors.toList());
//        Map<String, Class<?>> CLASS_HASH_MAP = new HashMap<>();
//        for (BeanDefinition b : beanDefins) {
//            try {
//                Class<?> clazz = this.getClass().getClassLoader().loadClass(b.getBeanClassName());
//                CoreBankParam param = clazz.getAnnotation(CoreBankParam.class);
//                Class<?> exitClass = CLASS_HASH_MAP.putIfAbsent(param.paramName(), clazz);
//                if (exitClass != null) {
//                    throw new RuntimeException("there is duplicate paramName[" + param.paramName() + "] in " + clazz + "," + exitClass);
//                }
//                ParamConfig config = new ParamConfig(param);
//                config.setParamType(clazz);
//                if (param.loadAll()) {
//                    config.setProviderService(context.getBean(param.provider()));
//                } else {
//                    config.setProviderService(new DefaultRemoteParamProvider(config));
//                }
//
//                boolean exitMainNode = mainNodeParams.parallelStream().anyMatch(i -> i.getParaCode().equals(config.getParamName()))
//                if (!exitMainNode) {
//                    try {
//                        ParamCenterServiceImpl.this.nodeSyncMapper.insertMainNodeInfo(config.getParamName());
//                    } catch (DuplicateKeyException e) {
//                        LOGGER.error("insert main nodeinfo for paramname={} get duplicateKeyException then update!", config.getParamName(), e);
//                    } catch (Exception e) {
//                        throw e;
//                    }
//                }
//
//                //非全量加载数据，更新节点时间....
//                if (!config.isLoadAll()) {
//                    ParamCenterServiceImpl.this.updateNodeInfo(config.getParamName(), ToolUtil.getNodeName);
//                }
//                paramConfigs.put(param.paramName(), config);
//                ParamConfig.saveWarpClass(clazz);
//            } catch (Exception e) {
//                LOGGER.error("loadParamConfig {} fail ", b.getBeanClassName(), e);
//                throw new Exception("启动参数加载失败" +  e);
//            }
//        }
    }

    /**
     * 初始加载参数
     *
     * @param delay
     */
    private void loadParamImmediately(long delay) {
        rtpAsyncService.getExecutor().execute(() -> {
            try {
                TimeUnit.MICROSECONDS.sleep(delay);
            } catch (Throwable e) {
                LOGGER.error("sleep fail in loadParamImmediately!", e);
            }
            LOGGER.info("=============== load params ================");
            ParamCenterServiceImpl.this.paramConfigs.values().stream().filter(v -> !v.isLazy() && v.isCache() && v.isLoadAll()).forEach(c -> {
                try {
                    ParamCenterServiceImpl.this.list(c.getParamName());
                } catch (Throwable e) {
                    LOGGER.error("loadParam {} fail ", c.getParamName(), e);
                }
            });
        });
    }

    private void checkLocalParamIsUpdate(final BiFunction<Map<String, List<ParamNodeBean>>, List<String>, Consumer<ParamConfig>> checkFunc) {
//        String nodeName = ToolUtil.getNodeName();
//        scheduledExecutor.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    //获取MAIN 节点以及当前节点数据
//                    List<ParamNodeBean> nodeBeanList = ParamCenterServiceImpl.this.nodeSyncMapper.queryMyNodeAndMainNode(nodeName);
//                    checkMainNodeCacheTime(nodeBeanList);
//                    Map<String, List<ParamNodeBean>> paramNodes = nodeBeanList.stream().collect(Collectors.groupingBy((p) -> p.getParaCode()));
//                    paramNodes.values().forEach(beans -> {
//                        beans.sort((a, b) -> {
//                            if (Objects.equals(MAIN_NODE, a.getNodeName())) {
//                                return -1;
//                            } else if (Objects.equals(MAIN_NODE, b.getNodeName())) {
//                                return 1;
//                            }
//                            return a.getNodeName().compareTo(b.getNodeName());
//                        });
//                    });
//                    List<String> updated = new ArrayList<>();
//                    ParamCenterServiceImpl.this.paramConfigs.values().stream().filter(v -> v.isCache()).forEach(checkFunc.apply(paramNodes, updated));
//                    updated.stream().forEach(p -> {
//                        ParamConfig config = ParamCenterServiceImpl.this.paramConfigs.get(p);
//                        if (config != null) {
//                            try {
//                                if (config.isLoadAll() && config.isCache()) {
//                                    ParamCenterServiceImpl.this.listForUpdate(config.getParamName());
//                                } else {
//                                    coreCache.remove(config.getCacheKey(), config.getClazzOfDataKind());
//                                    //更新 本地节点 以表示 收到更新通知
//                                    ParamCenterServiceImpl.this.updateNodeInfo(config.getParamName(), ToolUtil.getNodeName);
//                                }
//                            } catch (Throwable e) {
//                                LOGGER.error("updata LocalParam {} fail !", config.getParamName(), e);
//                            }
//                        }
//                    });
//                } catch (Throwable e) {
//                    LOGGER.error("checkLocalParamIsUpdated fail !", e);
//                }
//            }
//        }, 5 * DELAY_CHECK, DELAY_CHECK, TimeUnit.MILLISECONDS);
    }

    private <T extends ParamBean> List<T> listForUpdate(String paramName) {
        ParamConfig config = paramConfigs.get(paramName);
        Optional<Map<String, T>> optionalList = loadData(paramName, config, Optional.ofNullable(null));
        List<T> result = new ArrayList<>();
        result.addAll(optionalList.orElse(new HashMap<>()).values());
        if (LOGGER.isDebugEnabled()) {
            int maxIndex = result.size();
            if (result.size() > 5) {
                maxIndex = 5;
            }
            LOGGER.debug("list param={} values={} ...", paramName, result.subList(0, maxIndex));
        }

        return result;
    }

    /**
     * 定时任务加载参数不需要加自旋锁
     *
     * @param paramName
     * @param config
     * @param paramInCache
     * @param <T>
     * @return
     */
    private <T extends ParamBean> Optional<Map<String, T>> loadData(String paramName, ParamConfig config, Optional<Map<String, T>> paramInCache) {
        try {
            List<T> data = config.getProviderService().list();
            LOGGER.info("{} {} load data list is {}", paramName, config.getProviderService(), data == null ? 0 : data.size());
            if (data != null && data.size() > 0) {
                data = wrapParam(data);
                Map<String, T> mapValue = conver2Map(data, paramName);
                paramInCache = Optional.of(mapValue);
                saveToCache(paramName, mapValue);
            }
            return paramInCache;
        } catch (Exception e) {
            LOGGER.error(config.getCacheKey() + "loadData exception", e);
        }
        return paramInCache;
    }

    /**
     * 从运营中心加载有更新的远程参数
     *
     * @param nodeBeanList
     */
    private void checkMainNodeCacheTime(List<ParamNodeBean> nodeBeanList) {
//        try {
//            List<ParamNodeBean> mainNodeBeanList = nodeBeanList.stream().filter(x -> MAIN_NODE.equals(x.getNodeName())).collect(Collectors.toList());
//            List<MainNodeInfo> remoteMainNodeParamList = getRemoteMainNodeParamList();
//            if (JudgeUtils.isNotNull(remoteMainNodeParamList)) {
//                Map<String, String> mainNodeParamMap = remoteMainNodeParamList.stream()
//                        .collect(Collectors.toMap(MainNodeInfo::getParamCode, MainNodeInfo::getLastCacheTime, (key1, key2) -> key2));
//                mainNodeBeanList.stream().forEach(item -> {
//                    if (JudgeUtils.isLT(item.getLastCacheTime(), mainNodeParamMap.get(item.getNodeCode()))) {
//                        item.setLastCacheTime(mainNodeParamMap.get(item.getParaCode()));
//                        updateMainNodeInfo(item);//更新数据库MAIN节点
//                    }
//                });
//            }
//        } catch (Exception e) {
//            LOGGER.error("update mainNode lastCacheTime fail !", e);
//        }
    }

    private void updateNodeInfo(ParamNodeBean item) {
        try {
            int exitFlag = ParamCenterServiceImpl.this.nodeSyncMapper.updateNodeInfo(item);
            if (exitFlag == 0) {
                ParamCenterServiceImpl.this.nodeSyncMapper.insertNodeInfo(item);
            }
        } catch (DuplicateKeyException e) {
            LOGGER.error("update nodeInfo={} get duplicateKeyException then update!", item, e);
            ParamCenterServiceImpl.this.nodeSyncMapper.updateNodeInfo(item);
        }
    }

    private void updateMainNodeInfo(ParamNodeBean item) {
        try {
            ParamCenterServiceImpl.this.nodeSyncMapper.updateNodeInfo(item);
        } catch (Exception e) {
            LOGGER.error("update nodeInfo={} fail!", item, e);
        }
    }

    private List<MainNodeInfo> getRemoteMainNodeParamList() {
        MainNodeParamReqDTO paramReqDTO = new MainNodeParamReqDTO();
//        paramReqDTO.setHead(new BaseHead());
//        paramReqDTO.getHead().setServcCode(REMOTE_PARAM_SERVICE_CODE);
        MainNodeParamRespDTO paramRespDTO = null;
//        try {
//            paramRespDTO = RpcUtil.execute(paramReqDTO, MainNodeParamRespDTO.class);
//        } catch (Exception e) {
//            LOGGER.error("update MainNodeParam from OPCT fail!", e);
//        }
        return paramRespDTO.getParamNodeBeanList();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        if (event.getApplicationContext().getParent() == null) {
//            try {
//                this.loadParamConfig(event.getApplicationContext());
//                this.loadParamImmediately(DELAY_LOAD);
//                this.checkLocalParamIsUpdate((paramNodes1, updated1) -> c -> {
//                    try {
//                        boolean needUpdate = false;
//                        List<ParamNodeBean> v = paramNodes1.get(c.getParamName());
//                        if (v != null) {
//                            //未记录本节点，同步记录{}
//                            if (v.size() == 1 && Objects.equals(MAIN_NODE, v.get(0).getNodeName())) {
//                                LOGGER.info("{} need update ,while ctrl dosn't has this nodeInfo?", c.getParamName());
//                                needUpdate = true;
//                            }
//                            //已经同步本节点记录，但是根节点数据已经比本节点新
//                            if (v.size() > 1 && v.get(0).getLastCacheTime().compareTo(v.get(1).getLastCacheTime()) > 0) {
//                                LOGGER.info("{} need update ,while ctrl show MainNode catchTime{} is new than this node cacheTime {}!", c.getParamName(), v.get(0).getLastCacheTime(), v.get(1).getLastCacheTime());
//                                needUpdate = true;
//                            }
//                            if (needUpdate) {
//                                LOGGER.info("{} need update !", c.getParamName());
//                                updated1.add(c.getParamName());
//                            }
//                        } else {
//                            LOGGER.info("{} dosn't need update , while ctrl dosn't has this paramInfo!", c.getParamName());
//                        }
//                    } catch (Exception e) {
//                        LOGGER.error("checkLocalParam {} fail ", c.getParamName(), e);
//                    }
//                });
////                ParamCenterLoadedEvent paramCenterLoadedEvent = new ParamCenterLoadedEvent("ParamCenter参数加载成功！");
////                event.getApplicationContext().publishEvent(paramCenterLoadedEvent);
//            } catch (Exception e) {
//                try {
//                    throw new Exception("启动参数加载失败", e);
//                } catch (Exception exception) {
//                    exception.printStackTrace();
//                }
//            }
//        } else {
//            LOGGER.error("============= load params Configs is not in time...wait...===============");
//        }
    }

    private static <T> T wrapParam(T param) {
        Class<? extends ParamBeanWrap> clazz = ParamConfig.generWarpClass(param.getClass());
        if (clazz != null) {
            try {
                ParamBeanWrap obj = clazz.newInstance();
                obj.setTarget(param);
                obj.modifySetFlag(true);
                BeanUtils.copyProperties(param, obj);
                obj.modifySetFlag(false);
                return (T) obj;
            } catch (Exception e) {
                return param;
            }
        }
        return param;
    }

    private static <T> List<T> wrapParam(List<T> params) {
        Class<?> clazz = ParamConfig.generWarpClass(params.get(0).getClass());
        if (clazz != null) {
            List<T> result = new ArrayList<>();
            params.forEach(p -> result.add(wrapParam(p)));
            return result;
        }
        return params;
    }
}
