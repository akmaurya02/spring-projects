package com.app.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ApplicationCacheDemoApplication {
    public static void main(String[] args) { SpringApplication.run(ApplicationCacheDemoApplication.class, args); }

}
