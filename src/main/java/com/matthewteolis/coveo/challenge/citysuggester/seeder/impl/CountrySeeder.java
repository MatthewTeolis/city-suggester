package com.matthewteolis.coveo.challenge.citysuggester.seeder.impl;

import com.matthewteolis.coveo.challenge.citysuggester.entity.country.Country;
import com.matthewteolis.coveo.challenge.citysuggester.repository.CountryRepository;
import com.matthewteolis.coveo.challenge.citysuggester.seeder.AbstractSeeder;

import java.util.List;
import java.util.Map;

public class CountrySeeder extends AbstractSeeder<CountryRepository> {

    private final Map<String, Country> countryMap;

    /**
     * @param countryRepository The repository for the {@link Country} object.
     * @param countryMap The map that contains the countryMap data.
     *
     * @implSpec Method takes a map because we have already built the map beforehand.
     * In order to not create a new list from the data file, we use the existing map.
     */
    public CountrySeeder(CountryRepository countryRepository, Map<String, Country> countryMap) {
        super(countryRepository);
        this.countryMap = countryMap;
    }

    /**
     * Seeds the countries data to the database.
     *
     * @return {@link List} of {@link Country} objects that were saved to the database.
     */
    @Override
    public List<Country> seed() {
        return repository.saveAll(countryMap.values());
    }
}