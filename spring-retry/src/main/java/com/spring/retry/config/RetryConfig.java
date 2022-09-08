package com.spring.retry.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.Collections;

@Configuration
public class RetryConfig {

    @Bean(name = "fixedBackOffRetryTemplate")
    public RetryTemplate retryTemplate() {

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(5, Collections.singletonMap(Exception.class, true));

        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(1000L);

        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

        return retryTemplate;
    }

    @Bean(name = "exponentialBackOffRetryTemplate")
    public RetryTemplate getExponentialBackoffRetryTemplate() {

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(4, Collections.singletonMap(Exception.class, true));

        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(5000L);
        backOffPolicy.setMaxInterval(50000L);
        backOffPolicy.setMultiplier(2D);

        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        return retryTemplate;
    }


}
