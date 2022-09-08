package com.redis.pusub.config;

public interface MessagePublisher {
    void publish(final String message);
}
