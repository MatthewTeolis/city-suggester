package com.matthewteolis.coveo.challenge.citysuggester.repository;

import com.matthewteolis.coveo.challenge.citysuggester.entity.city.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findAllByNameStartingWith(String name);

}
