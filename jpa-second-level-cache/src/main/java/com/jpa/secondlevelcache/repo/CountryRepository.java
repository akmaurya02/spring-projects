package com.jpa.secondlevelcache.repo;

import com.jpa.secondlevelcache.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    @Query("Select c from Country c where c.countryName like %:countryName%")
    @QueryHints({@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true")})
    Country findCountryByName(String countryName);

    long findTotalCount();
}