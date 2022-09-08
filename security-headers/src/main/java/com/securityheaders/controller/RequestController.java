package com.securityheaders.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {

    @GetMapping(value = "/api/v1/testRequest")
    public ResponseEntity<String> testRequest() {
        System.out.println("test request");
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping(value = "/api/v1/testSecurity")
    public ResponseEntity<String> testSecurity() {
        System.out.println("test security");
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
