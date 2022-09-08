package com.app.cache.util;

import org.springframework.scheduling.support.CronExpression;

import java.time.LocalDateTime;

public class ValidateCronExpression {
    public static void main(String[] args) {
        String cronExpression = "0 0 0 1 1/3 *";
        CronExpression cronTrigger = CronExpression.parse(cronExpression);
        System.out.println("Next 5 Execution Time::");
        LocalDateTime date = LocalDateTime.now();
        for (int i = 0; i < 5; i++) {
            date = cronTrigger.next(date);
            System.out.println(date);
        }

    }
}
