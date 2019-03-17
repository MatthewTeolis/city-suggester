package com.matthewteolis.coveo.challenge.citysuggester.seeder.impl;

import com.matthewteolis.coveo.challenge.citysuggester.entity.admin1.Admin1;
import com.matthewteolis.coveo.challenge.citysuggester.entity.city.City;
import com.matthewteolis.coveo.challenge.citysuggester.entity.city.CityFactory;
import com.matthewteolis.coveo.challenge.citysuggester.entity.country.Country;
import com.matthewteolis.coveo.challenge.citysuggester.repository.Admin1CodeRepository;
import com.matthewteolis.coveo.challenge.citysuggester.repository.CityRepository;
import com.matthewteolis.coveo.challenge.citysuggester.seeder.AbstractSeeder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Admin1Seeder extends AbstractSeeder<Admin1CodeRepository> {

    private final Map<String, Admin1> admin1Map;

    /**
     * @param admin1CodeRepository The repository for the {@link Admin1} object.
     * @param admin1Map The map that contains the admin1 data.
     *
     * @implSpec Method takes a map because we have already built the map beforehand.
     * In order to not create a new list from the data file, we use the existing map.
     */
    public Admin1Seeder(Admin1CodeRepository admin1CodeRepository, Map<String, Admin1> admin1Map) {
        super(admin1CodeRepository);
        this.admin1Map = admin1Map;
    }

    /**
     * Seeds the admin1Codes data to the database.
     *
     * @return {@link List} of {@link Admin1} objects that were saved to the database.
     */
    public List<Admin1> seed() {
        return repository.saveAll(admin1Map.values());
    }

}