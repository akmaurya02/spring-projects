package com.spring.retry.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
public class RetryService {

   /* @Autowired
    @Qualifier(value = "fixedBackOffRetryTemplate")
    private RetryTemplate retryTemplate;*/

    @Autowired
    @Qualifier(value = "exponentialBackOffRetryTemplate")
    private RetryTemplate retryTemplate;

    @PostConstruct
    public void withRetryTemplate() {
        String result = retryTemplate.execute(context -> {
                    System.out.println("doWithRetry:: " + context.getRetryCount());
                    return checkRetry("");
                },
                context -> {
                    System.out.println("recover:: " + context.getRetryCount());
                    return checkRetry("Success");
                });

        System.out.println("result:: " + result);
    }

    public String checkRetry(String str) {
        System.out.println("checkRetry:: " + new Date());
        if (StringUtils.isEmpty(str)) {
            throw new IllegalArgumentException();
        }
        return str;
    }
}
