package com.spring.customapp.thread;

import com.spring.customapp.config.RedisConf;
import com.spring.customapp.service.CustomService;

import java.util.concurrent.ThreadPoolExecutor;

public class ListenerThread extends Thread {

    private boolean valid = true;
    private CustomService customService;
    private RedisConf redisConf;

    public ListenerThread(CustomService customService, RedisConf redisConf) {
        this.customService = customService;
        this.redisConf = redisConf;
    }

    @Override
    public void run() {
        while (valid) {
            try {
                ThreadPoolExecutor threadPoolExecutor = ThreadPool.getInstance().getThreadPoolExecutor();
                threadPoolExecutor.execute(new WorkerThread(customService, redisConf));
                Thread.sleep(1000);
            } catch (Exception ex) {
                System.out.println("ERROR IN LISTENER THREAD:: " + ex.getMessage());
            }
        }
    }

    public void finish() {
        valid = false;
    }
}