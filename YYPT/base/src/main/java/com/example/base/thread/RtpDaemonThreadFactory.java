package com.example.base.thread;

/**
 * RTP守护线程工厂
 */
public class RtpDaemonThreadFactory extends RtpThreadFactory {

    public RtpDaemonThreadFactory(String threadGroupName) {
        super(threadGroupName);
    }

    @Override
    public Thread newThread(Runnable r) {
       Thread thread = super.newThread(r);
       thread.setDaemon(true);
       return thread;
    }

}
