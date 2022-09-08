package com.spring.customapp.controller;


import com.spring.customapp.dto.CustomAppRequestDTO;
import com.spring.customapp.service.CustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CustomAppController {

    @Autowired
    private CustomService customService;

    @PostMapping(value = "/api/v1/test")
    public ResponseEntity<String> receiveRequest(@Validated @RequestBody CustomAppRequestDTO customAppRequestDTO) {
        customService.processCustomAppRequest(customAppRequestDTO);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
