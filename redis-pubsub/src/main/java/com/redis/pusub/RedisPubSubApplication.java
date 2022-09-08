package com.redis.pusub;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RedisPubSubApplication {

    public static void main(String[] args) { SpringApplication.run(RedisPubSubApplication.class, args); }

}
