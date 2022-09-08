package com.redis.pusub.service;

import com.redis.pusub.config.RedisMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PubSubService {

    @Autowired
    private RedisMessagePublisher redisMessagePublisher;

    @Scheduled(fixedRate = 1000)
    private void scheduleFixedRateTask() {
        String message = "Message " + UUID.randomUUID();
        redisMessagePublisher.publish(message);
    }
}
