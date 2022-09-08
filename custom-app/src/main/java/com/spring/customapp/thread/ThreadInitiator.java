package com.spring.customapp.thread;

import com.spring.customapp.config.RedisConf;
import com.spring.customapp.service.CustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.Objects;

@Component
public class ThreadInitiator {

    @Autowired
    private CustomService customService;

    @Autowired
    private RedisConf redisConf;


    private ListenerThread listenerThread;

    @PostConstruct
    private void init() {
        ThreadPool.getInstance();
        listenerThread = new ListenerThread(customService, redisConf);
        listenerThread.start();
        System.out.println("Thread is initiated...");
    }

    @PreDestroy
    public void onDestroy() {
        System.out.println("Shutting Down Thread Pool please wait...");
        if (Objects.nonNull(listenerThread)) {
            listenerThread.finish();
        }
        if (Objects.nonNull(redisConf)) {
            redisConf.closeConnection();
        }
        ThreadPool.shutdown();
        deRegisterDatabaseDriver();
    }

    public void deRegisterDatabaseDriver() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            if (driver.getClass().getClassLoader() == cl) {
                try {
                    DriverManager.deregisterDriver(driver);
                    System.out.println("Deregister JDBC driver :: " + driver);
                } catch (Exception ex) {
                    System.out.println("ERROR IN DEREGISTER JDBC DRIVER:: " + driver);
                    System.out.println(ex.getMessage());
                }
            } else {
                System.out.println("Not deregister JDBC driver " + driver + " as it does not belong to this webapp's ClassLoader");
            }
        }
    }
}
