package com.spring.customapp.thread;

import com.spring.customapp.constants.AppConstants;
import com.spring.customapp.config.RedisConf;
import com.spring.customapp.service.CustomService;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class WorkerThread implements Runnable {
    private static final Map<String, String> processingQueueLocks = new ConcurrentHashMap<>();
    private Object object = new Object();
    private CustomService customService;
    private RedisConf redisConf;

    public WorkerThread(CustomService customService, RedisConf redisConf) {
        this.customService = customService;
        this.redisConf = redisConf;
    }

    public void run() {
        Jedis jedisConnection = null;
        String dataFromQueue = null;
        try {
            synchronized (object) {
                String processingQueueName = AppConstants.PROCESSING_QUEUE;
                String processingQueueInCache = processingQueueLocks.get(processingQueueName);
                if (StringUtils.isBlank(processingQueueInCache)) {
                    processingQueueLocks.put(processingQueueName, processingQueueName);
                    jedisConnection = redisConf.getResource();
                    List<String> data = jedisConnection.lrange(processingQueueName, 0, 0);
                    if (Objects.nonNull(data) && !data.isEmpty()) {
                        dataFromQueue = data.get(0);
                    } else {
                        String requestData = jedisConnection.rpoplpush(AppConstants.DATA_QUEUE, processingQueueName);
                        if (requestData != null) {
                            dataFromQueue = requestData;
                        }
                    }
                    if (StringUtils.isNotEmpty(dataFromQueue)) {
                        customService.processQueueData(dataFromQueue);
                        jedisConnection.expire(processingQueueName, 0);
                        System.out.println("EXPIRED DATA FROM PROCESSING QUEUE:: " + processingQueueName);
                    }
                    processingQueueLocks.remove(processingQueueName);
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR IN WORKER THREAD:: DATA:: "+ dataFromQueue + ", EXCEPTION:: " + e);
        } finally {
            if (Objects.nonNull(jedisConnection)) {
                jedisConnection.close();
            }
        }
    }
}
