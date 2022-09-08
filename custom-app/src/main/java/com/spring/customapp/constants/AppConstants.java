package com.spring.customapp.constants;

public class AppConstants {

    private AppConstants() {
        throw new IllegalStateException("AppConstants is a Utility class");
    }

    // Thread
    public static final int THREAD_POOL_CORE_SIZE = 10;
    public static final int THREAD_POOL_MAX_SIZE = 100;
    public static final int THREAD_POOL_AWAIT_TERMINATION_TIME = 1;


    // Redis
    public static final String REDIS_HOST = "";
    public static final String REDIS_PWD = "";
    public static final int REDIS_PORT = 6379;
    public static final int REDIS_MIN_EVICTION_IDLE_TIME = 60000;
    public static final boolean REDIS_TEST_ON_BORROW = true;
    public static final boolean REDIS_TEST_ON_RETURN = true;
    public static final int REDIS_MAX_WAIT_MILLIS = 5000;
    public static final int REDIS_MAX_TOTAL = 200;
    public static final int REDIS_MAX_IDLE = 10;
    public static final int REDIS_MIN_IDLE = 1;

    public static final String DATA_QUEUE = "data-queue";
    public static final String PROCESSING_QUEUE = "processing-queue";
}
