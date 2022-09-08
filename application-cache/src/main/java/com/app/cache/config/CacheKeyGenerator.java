package com.app.cache.config;


import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;


public class CacheKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        Object object = generateKey(params);
        System.out.println("Generated Cache Key :: " + object);
        return object;
    }

    /**
     * Generate a key based on the specified parameters.
     */
    public static Object generateKey(Object... params) {
        if (params.length == 0) {
            return CustomCacheKey.EMPTY;
        }
        if (params.length == 1) {
            Object param = params[0];
            if (param != null && !param.getClass().isArray()) {
                return param;
            }
        }
        return new CustomCacheKey(params);
    }
}
