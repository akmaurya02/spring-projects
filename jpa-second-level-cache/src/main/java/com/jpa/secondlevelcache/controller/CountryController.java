package com.jpa.secondlevelcache.controller;

import com.jpa.secondlevelcache.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class CountryController {
    @Autowired
    private CountryService countryService;

    @PostMapping("/country")
    public ResponseEntity<Object> saveCountry() {
        return new ResponseEntity<>(countryService.saveCountry(), HttpStatus.CREATED);
    }

    @GetMapping("/country/{id}")
    public ResponseEntity<Object> getCountryById(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(countryService.getCountryById(id), HttpStatus.OK);
    }

    @GetMapping("/country")
    public ResponseEntity<Object> getCountries() {
        return new ResponseEntity<>(countryService.getAllCountries(), HttpStatus.OK);
    }

    @GetMapping("/countryByName")
    public ResponseEntity<Object> getCountryByName(@RequestParam(name = "countryName") String countryName) {
        return new ResponseEntity<>(countryService.getCountryByName(countryName), HttpStatus.OK);
    }

    @GetMapping("/getTotalCountryCount")
    public ResponseEntity<Object> getTotalCountryCount() {
        return new ResponseEntity<>(countryService.getTotalCountryCount(), HttpStatus.OK);
    }

    @GetMapping("/countryAndCityDetails")
    public ResponseEntity<String> getCountryAndCityDetails() {
        countryService.getCountryAndCityDetails();
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}