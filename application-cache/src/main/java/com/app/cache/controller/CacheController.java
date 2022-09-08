package com.app.cache.controller;

import com.app.cache.dto.Customer;
import com.app.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @PostMapping("/customer")
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
        cacheService.addCustomer(customer);
        return new ResponseEntity<>("Customer Added", HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        Customer customer = cacheService.getCustomer(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/testCache/{id}")
    public ResponseEntity<String> testCache(@PathVariable Long id) {
        String result = cacheService.testCache(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getCacheDetails")
    public ResponseEntity<Object> getCacheDetails(@RequestParam String cacheName, @RequestParam Long key) {
        Object object = cacheService.getCacheDetails(cacheName, key);
        return new ResponseEntity<>(object, HttpStatus.OK);
    }

    @DeleteMapping("/clearAllCache")
    public ResponseEntity<Object> clearAllCache() {
        cacheService.clearAllCache();
        return new ResponseEntity<>("Cleared all cache", HttpStatus.OK);
    }
}
