package com.spring.customapp.config;

import com.spring.customapp.constants.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class RedisConf {

    @Autowired
    private Environment environment;

    private JedisPool pool = null;

    @PostConstruct
    public JedisPool getJedisConnectionPool() {
        try {
            JedisPoolConfig poolConfig = buildPoolConfig();
            if(Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> (env.equalsIgnoreCase("prod") || env.equalsIgnoreCase("stag")) ))
            {
                pool = new JedisPool(poolConfig, AppConstants.REDIS_HOST, AppConstants.REDIS_PORT, AppConstants.REDIS_MAX_WAIT_MILLIS, AppConstants.REDIS_PWD);
            } else {
                pool = new JedisPool(poolConfig, "localhost");
            }
            pool.getResource();
        } catch (Exception ex){
            System.out.println("FAILED TO CONNECT REDIS:: "+ ex.getMessage());
        }
        return pool;
    }


    private JedisPoolConfig buildPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(AppConstants.REDIS_MAX_TOTAL);
        poolConfig.setMinEvictableIdleTimeMillis(AppConstants.REDIS_MIN_EVICTION_IDLE_TIME);
        poolConfig.setTestOnBorrow(AppConstants.REDIS_TEST_ON_BORROW);
        poolConfig.setTestOnReturn(AppConstants.REDIS_TEST_ON_RETURN);
        poolConfig.setMaxWaitMillis(AppConstants.REDIS_MAX_WAIT_MILLIS);
        poolConfig.setMaxIdle(AppConstants.REDIS_MAX_IDLE);
        poolConfig.setMinIdle(AppConstants.REDIS_MIN_IDLE);
        return poolConfig;
    }

    public void closeConnection() {
        if (pool != null) {
            pool.destroy();
            System.out.println("REDIS POOL DESTROYED");
        }
    }

    public Jedis getResource() {
        return pool.getResource();
    }

}



