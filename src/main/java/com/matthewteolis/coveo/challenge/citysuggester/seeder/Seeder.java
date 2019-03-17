package com.matthewteolis.coveo.challenge.citysuggester.seeder;

import com.matthewteolis.coveo.challenge.citysuggester.entity.admin1.Admin1;
import com.matthewteolis.coveo.challenge.citysuggester.entity.admin1.Admin1Factory;
import com.matthewteolis.coveo.challenge.citysuggester.entity.city.CityFactory;
import com.matthewteolis.coveo.challenge.citysuggester.entity.country.Country;
import com.matthewteolis.coveo.challenge.citysuggester.entity.country.CountryFactory;
import com.matthewteolis.coveo.challenge.citysuggester.repository.Admin1CodeRepository;
import com.matthewteolis.coveo.challenge.citysuggester.repository.CityRepository;
import com.matthewteolis.coveo.challenge.citysuggester.repository.CountryRepository;
import com.matthewteolis.coveo.challenge.citysuggester.seeder.impl.Admin1Seeder;
import com.matthewteolis.coveo.challenge.citysuggester.seeder.impl.CitySeeder;
import com.matthewteolis.coveo.challenge.citysuggester.seeder.impl.CountrySeeder;
import com.matthewteolis.coveo.challenge.citysuggester.util.CitySuggesterResourceUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class Seeder {

    // Repository declaration
    private final Admin1CodeRepository admin1CodeRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    // Factory declaration
    private final Admin1Factory admin1Factory;
    private final CityFactory cityFactory;
    private final CountryFactory countryFactory;

    private final CitySuggesterResourceUtils resourceUtils;

    @Autowired
    public Seeder(
            Admin1CodeRepository admin1CodeRepository,
            CityRepository cityRepository,
            CountryRepository countryRepository,
            Admin1Factory admin1Factory,
            CityFactory cityFactory,
            CountryFactory countryFactory,
            CitySuggesterResourceUtils resourceUtils

    ) {
        this.admin1CodeRepository = admin1CodeRepository;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.admin1Factory = admin1Factory;
        this.cityFactory = cityFactory;
        this.countryFactory = countryFactory;
        this.resourceUtils = resourceUtils;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        try {
            final Map<String, Admin1> admin1Map = buildAdmin1Map();
            final Map<String, Country> countryMap = buildCountryMap();

            final Seedable[] seeders = new AbstractSeeder[] {
                    new Admin1Seeder(admin1CodeRepository, admin1Map),
                    new CountrySeeder(countryRepository, countryMap),

                    // CitySeeder depends on Admin1Seeder and CountrySeeder
                    new CitySeeder(cityRepository, cityFactory, admin1Map, countryMap, getCityData())
            };

            seedAll(seeders);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Seed all the seeders in the list.
     *
     * @param seeders Array of {@link Seedable} objects.
     */
    private void seedAll(Seedable[] seeders) {
        for (Seedable seeder : seeders) {
            seedIfEmpty(seeder);
        }
    }

    /**
     * Seeds the database table if the table is empty.
     *
     * @param seeder The seeder to use for seeding the database.
     */
    private void seedIfEmpty(Seedable seeder) {
        if (seederRepositoryIsEmpty(seeder)) {
            seeder.seed();
        }
    }

    /**
     * Checks if the repository is empty.
     *
     * @param seeder The repository that is used by the seeder.
     * @return {@code true} if the repository is empty, {@code false} otherwise.
     */
    private boolean seederRepositoryIsEmpty(Seedable seeder) {
        return seeder.getRepository().count() == 0;
    }

    /**
     * Parses the admin1 data from the CSV file and stores it in a map, with the key being the code of the admin1.
     *
     * @return A {@link HashMap} of the {@link Admin1} objects.
     * @throws IOException If the countries file cannot be read.
     */
    private Map<String, Admin1> buildAdmin1Map() throws IOException {
        final Map<String, Admin1> admin1CodeMap = new HashMap<>();

        final CSVParser parser = CSVFormat.DEFAULT.withDelimiter('\t').withFirstRecordAsHeader().parse(resourceUtils.getFile("admin1CodesASCII.tsv"));

        for (CSVRecord record : parser) {
            final String code = record.get("code");
            final String name = record.get("name");

            admin1CodeMap.put(code, admin1Factory.create(code, name));
        }

        return admin1CodeMap;
    }

    /**
     * Parses the country data from the CSV file and stores it in a map, with the key being the ISO of the country.
     *
     * @return A {@link HashMap} of the {@link Country} objects.
     * @throws IOException If the countries file cannot be read.
     */
    private Map<String, Country> buildCountryMap() throws IOException {
        final Map<String, Country> countryMap = new HashMap<>();

        final CSVParser parser = CSVFormat.DEFAULT.withDelimiter('\t').withFirstRecordAsHeader().parse(resourceUtils.getFile("countryInfo.tsv"));

        for (CSVRecord record : parser) {
            final String ISO = record.get("ISO");
            final String name = record.get("Country");

            countryMap.put(ISO, countryFactory.create(ISO, name));
        }

        return countryMap;
    }

    /**
     * Retrieves the data for the cities.
     *
     * @return A {@link CSVParser} of the cities.
     * @throws IOException If the cities file cannot be read.
     */
    private CSVParser getCityData() throws IOException {
        return CSVFormat.DEFAULT.withDelimiter('\t').withFirstRecordAsHeader().withQuote(null).parse(resourceUtils.getFile("cities_canada-usa.tsv"));
    }

}
