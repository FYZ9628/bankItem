package com.example.base.thread;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Component
public class RtpAsyncService {
    private ExecutorService executor;

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public String toString() {
        return "RtpAsyncService{" +
                "executor=" + executor +
                '}';
    }
}
