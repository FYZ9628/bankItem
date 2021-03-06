package com.example.opct_notice.notice.show;

import com.example.opct_notice.notice.constant.NoticeConst;
import com.example.opct_notice.notice.service.impl.AbstractGeneralNoticeService;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class SyncErrorMessageShower extends AbstractGeneralNoticeService implements ApplicationListener<ContextRefreshedEvent> {
    private final ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1,new ThreadFactoryBuilder().setNameFormat("SyncErrorMessage-%d").build());

    @Autowired
    private SyncErrorMessageThread syncErrorMessageThread;

//    @Override
    public String getConfigName() {
        return NoticeConst.ERROR_MSG_DO_RECEIVE_NOTICE;
    }

//    @Override
//    public void doShower() {
//        scheduledExecutorService.scheduleAtFixedRate(syncErrorMessageThread, 1, 10, TimeUnit.MINUTES);
//    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        scheduledExecutorService.scheduleAtFixedRate(syncErrorMessageThread, 1, 10, TimeUnit.MINUTES);
        scheduledExecutorService.scheduleAtFixedRate(syncErrorMessageThread, 10, 600, TimeUnit.SECONDS);
    }
}
