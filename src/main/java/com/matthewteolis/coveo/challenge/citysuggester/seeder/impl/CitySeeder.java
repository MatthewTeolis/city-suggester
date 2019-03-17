package com.matthewteolis.coveo.challenge.citysuggester.seeder.impl;

import com.matthewteolis.coveo.challenge.citysuggester.entity.admin1.Admin1;
import com.matthewteolis.coveo.challenge.citysuggester.entity.city.City;
import com.matthewteolis.coveo.challenge.citysuggester.entity.city.CityFactory;
import com.matthewteolis.coveo.challenge.citysuggester.entity.country.Country;
import com.matthewteolis.coveo.challenge.citysuggester.repository.CityRepository;
import com.matthewteolis.coveo.challenge.citysuggester.seeder.AbstractSeeder;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CitySeeder extends AbstractSeeder<CityRepository> {

    private final CityFactory cityFactory;
    private final Map<String, Admin1> admin1Map;
    private final Map<String, Country> countryMap;
    private final CSVParser cityData;

    /**
     * @param cityRepository The repository for the {@link City} object.
     * @param cityFactory A factory to create {@link City} objects.
     * @param admin1Map Uses the {@link Map} to attach the relation between the {@link City} and the {@link Admin1} object.
     * @param countryMap Uses the {@link Map} to attach the relation between the {@link City} and the {@link Country} object.
     * @param cityData The data of all the cities.
     */
    public CitySeeder(
            CityRepository cityRepository,
            CityFactory cityFactory,
            Map<String, Admin1> admin1Map,
            Map<String, Country> countryMap,
            CSVParser cityData
    ) {
        super(cityRepository);
        this.cityFactory = cityFactory;
        this.admin1Map = admin1Map;
        this.countryMap = countryMap;
        this.cityData = cityData;
    }

    /**
     * Seeds the cities data to the database.
     *
     * @return A {@link List} of {@link City} objects that were saved to the database.
     */
    @Override
    public List<City> seed() {
        final List<City> cityList = new ArrayList<>();

        for (CSVRecord record : cityData) {
            final String name = record.get("name");
            final String latitude = record.get("lat");
            final String longitude = record.get("long");
            final String countryCode = record.get("country");
            final String admin1Code = record.get("admin1");
            final String population = record.get("population");
            final String elevation = record.get("elevation");

            final Country country = countryMap.get(countryCode);

            final String admin1CodeString = String.format("%s.%s", countryCode, admin1Code);
            final Admin1 admin1 = admin1Map.get(admin1CodeString);

            cityList.add(cityFactory.create(name, latitude, longitude, country, admin1, population, elevation));
        }

        return repository.saveAll(cityList);
    }

}