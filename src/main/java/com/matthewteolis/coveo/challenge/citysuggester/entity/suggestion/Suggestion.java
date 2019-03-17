package com.matthewteolis.coveo.challenge.citysuggester.entity.suggestion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.matthewteolis.coveo.challenge.citysuggester.entity.admin1.Admin1;
import com.matthewteolis.coveo.challenge.citysuggester.entity.city.City;
import com.matthewteolis.coveo.challenge.citysuggester.entity.country.Country;

@JsonPropertyOrder({"name", "latitude", "longitude", "score"})
public class Suggestion {

    @JsonIgnore
    private City city;
    private double score;

    public Suggestion() {
    }

    // Used by the factory.
    Suggestion(City city, double score) {
        this.city = city;
        this.score = score;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    /**
     * Get's the name of the location in the format that was given
     * <a href="https://github.com/coveo/backend-coding-challenge#sample-responses">here</a>.
     *
     * @return The name of the {@link City}, {@link Admin1}, and {@link Country}.
     */
    public String getName() {
        final Admin1 admin1 = city.getAdmin1();
        final Country country = city.getCountry();

        String admin1Name = admin1.getName();
        String countryName = country.getName();

        if (country.getISO().equals("US")) {
            admin1Name = admin1.getCode().split("\\.")[1];
            countryName = "USA";
        }

        return String.format("%s, %s, %s", city.getName(), admin1Name, countryName);
    }

    /**
     * @return The latitude for the {@link City} in a {@link String} format.
     */
    public String getLatitude() {
        return String.valueOf(city.getLatitude());
    }

    /**
     * @return The longitude for the {@link City} in a {@link String} format.
     */
    public String getLongitude() {
        return String.valueOf(city.getLongitude());
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

}
