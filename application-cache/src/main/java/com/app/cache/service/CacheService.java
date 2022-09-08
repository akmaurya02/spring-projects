package com.app.cache.service;

import com.app.cache.config.CacheKeyGenerator;
import com.app.cache.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CacheService {

    @Autowired
    private CacheManager cacheManager;

    private final Map<Long, Customer> customers = new HashMap<>();

    public void addCustomer(Customer customer) {
        System.out.println("Add customer for id: " + customer.getId());
        customers.put(customer.getId(), customer);
    }

    @Cacheable(value = "customer", keyGenerator = "cacheKeyGenerator")
    public Customer getCustomer(Long id) {
        System.out.println("Get customer for id: " + id);
        return customers.get(id);
    }

    @Cacheable(value = "testCache", keyGenerator = "cacheKeyGenerator")
    public String testCache(Long id) {
        return "Cached data for id - " + id;
    }

    public Object getCacheDetails(String cacheName, Long key) {
        Object generatedKey = CacheKeyGenerator.generateKey(key);
        System.out.println("GeneratedKey:: " + generatedKey);
        Cache cache = cacheManager.getCache(cacheName);
        if ("customer".equals(cacheName)) {
            Customer customer = cache.get(generatedKey, Customer.class);
            System.out.println("customer:: " + customer);
            return customer;
        } else {
            String result = cache.get(generatedKey, String.class);
            System.out.println("result:: " + result);
            return result;
        }
    }

    @CacheEvict(cacheNames = {"customer", "testCache"}, allEntries = true)
    public void clearAllCache() {
        System.out.println("Cleared all cache");
    }

}
