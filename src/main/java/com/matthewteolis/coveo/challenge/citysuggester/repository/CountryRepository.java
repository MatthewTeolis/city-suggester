package com.matthewteolis.coveo.challenge.citysuggester.repository;

import com.matthewteolis.coveo.challenge.citysuggester.entity.country.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

}
