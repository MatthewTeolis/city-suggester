package com.matthewteolis.coveo.challenge.citysuggester.service;

import com.matthewteolis.coveo.challenge.citysuggester.entity.city.City;
import com.matthewteolis.coveo.challenge.citysuggester.entity.suggestion.Suggestion;
import com.matthewteolis.coveo.challenge.citysuggester.entity.suggestion.SuggestionFactory;
import com.matthewteolis.coveo.challenge.citysuggester.repository.CityRepository;
import com.matthewteolis.coveo.challenge.citysuggester.util.HaversineCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SuggestionService {

    @Value("${suggestion_service.score.distance_weight}")
    private double distanceWeight;

    @Value("${suggestion_service.score.population_weight}")
    private double populationWeight;

    private final CityRepository cityRepository;
    private final HaversineCalculator haversineCalculator;
    private final SuggestionFactory suggestionFactory;

    @Autowired
    public SuggestionService(CityRepository cityRepository, HaversineCalculator haversineCalculator, SuggestionFactory suggestionFactory) {
        this.cityRepository = cityRepository;
        this.haversineCalculator = haversineCalculator;
        this.suggestionFactory = suggestionFactory;
    }

    /**
     * Compute a {@link List} of {@link Suggestion}s for the user.
     *
     * @param query The name that the {@link City} starts with.
     * @param latitude The latitude of the caller.
     * @param longitude The longitude of the caller.
     * @return A {@link List} of {@link Suggestion}s.
     */
    public List<Suggestion> computeSuggestions(String query, double latitude, double longitude) {
        final List<City> cities = cityRepository.findAllByNameStartingWith(query);
        final List<Suggestion> suggestions = new ArrayList<>(cities.size());
        final long totalPopulation = computeTotalPopulation(cities);

        for (City city : cities) {
            double distance = haversineCalculator.distance(
                    latitude, longitude, 0,
                    city.getLatitude(), city.getLongitude(), city.getElevation());
            double distanceInKilometers = distance / 1000.0;

            suggestions.add(suggestionFactory.create(city, calculateScore(city, distanceInKilometers, totalPopulation)));
        }

        return suggestions;
    }

    /**
     * Compute the total population of all the cities given.
     *
     * @param cities The {@link List} of {@link City} objects.
     * @return The sum of population in all cities given.
     */
    private long computeTotalPopulation(List<City> cities) {
        long totalPopulation = 0;

        for (City city : cities) {
            totalPopulation += city.getPopulation();
        }

        return totalPopulation;
    }

    /**
     * Method to compute the confidence level for suggesting a given {@link City}.
     *
     * @param city The {@link City} to calculate the score for.
     * @param distance The distance between the given location and the {@link City}'s location.
     * @param totalPopulation The total population of all the cities.
     * @return The confidence score in percentage, ranges from {@code 0} - {@code 1}.
     */
    private double calculateScore(City city, double distance, long totalPopulation) {
        double distanceScore = distanceWeight * (1.0 / distance);
        double populationScore = populationWeight * ((double) city.getPopulation() / (double) totalPopulation);

        return distanceScore + populationScore;
    }

}
