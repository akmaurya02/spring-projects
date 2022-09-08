package com.jpa.secondlevelcache.service;

import com.jpa.secondlevelcache.dto.CountryDTO;
import com.jpa.secondlevelcache.entity.City;
import com.jpa.secondlevelcache.entity.Country;
import com.jpa.secondlevelcache.repo.CityRepository;
import com.jpa.secondlevelcache.repo.CountryRepository;
import org.hibernate.jpa.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CityRepository cityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public String saveCountry() {
        Country india = new Country();
        india.setCountryName("India");

        Set<City> indiaCities = new HashSet<>();
        City delhi = new City();
        delhi.setCityName("Delhi");
        delhi.setCountry(india);
        City pune = new City();
        pune.setCityName("Pune");
        pune.setCountry(india);

        indiaCities.add(delhi);
        indiaCities.add(pune);
        india.setCities(indiaCities);
        countryRepository.save(india);

        Country usa = new Country();
        usa.setCountryName("USA");

        Set<City> usaCities = new HashSet<>();
        City denver = new City();
        denver.setCityName("Denver");
        denver.setCountry(usa);
        City chicago = new City();
        chicago.setCityName("Chicago");
        chicago.setCountry(usa);

        usaCities.add(denver);
        usaCities.add(chicago);
        usa.setCities(usaCities);
        countryRepository.save(usa);

        return "Country data saved";
    }

    public CountryDTO getCountryById(Integer id) {
        Country country = countryRepository.findById(id).get();
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCountry(country.getCountryName());
        Set<String> cities = country.getCities().stream().map(City::getCityName).collect(Collectors.toSet());
        countryDTO.setCities(cities);
        return countryDTO;
    }

    public Set<CountryDTO> getAllCountries() {
        List<Country> countries = countryRepository.findAll();
        Set<CountryDTO> countryDTOs = new HashSet<>();
        countries.forEach(country -> {
            CountryDTO countryDTO = new CountryDTO();
            countryDTO.setCountry(country.getCountryName());
            Set<String> cities = country.getCities().stream().map(City::getCityName).collect(Collectors.toSet());
            countryDTO.setCities(cities);
            countryDTOs.add(countryDTO);
        });
        return countryDTOs;
    }

    public CountryDTO getCountryByName(String countryName) {
        Country country = countryRepository.findCountryByName(countryName);
        if (country == null) {
            return null;
        }
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCountry(country.getCountryName());
        Set<String> cities = country.getCities().stream().map(City::getCityName).collect(Collectors.toSet());
        countryDTO.setCities(cities);
        return countryDTO;
    }

    public long getTotalCountryCount() {
        return countryRepository.findTotalCount();
    }

    public void getCountryAndCityDetails() {
        String sql = "select  co.country_name as country_name, ci.city_name as city_name from country as co inner join city as ci on co.id = ci.country_id";
        List<Object[]> result = entityManager.createNativeQuery(sql)
                .setHint(QueryHints.HINT_CACHEABLE, true)
                .getResultList();
        for (Object[] data : result) {
            System.out.println("country: "+ data[0].toString());
            System.out.println("city: "+ data[1].toString());
            System.out.println("---------------------------------------");
        }
    }
}
