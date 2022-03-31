package com.example.base.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 跟进需要进行实例化线程对象
 */
public class RtpThreadFactory implements ThreadFactory {
    static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
    final ThreadGroup group;
    final AtomicInteger threadNumber = new AtomicInteger(1);
    final String namePrefix;

    public RtpThreadFactory(String threadGroupName) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup(): Thread.currentThread().getThreadGroup();
        namePrefix = threadGroupName + "-pool-" + POOL_NUMBER.getAndIncrement() + "-thread-";
    }

//    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
