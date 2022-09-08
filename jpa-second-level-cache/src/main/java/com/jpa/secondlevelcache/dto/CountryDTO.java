package com.jpa.secondlevelcache.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CountryDTO {
    private String country;
    private Set<String> cities;
}
