package com.matthewteolis.coveo.challenge.citysuggester.entity.city;

import com.matthewteolis.coveo.challenge.citysuggester.entity.admin1.Admin1;
import com.matthewteolis.coveo.challenge.citysuggester.entity.country.Country;
import org.springframework.stereotype.Component;

@Component
public class CityFactory {

    /**
     * Create a {@link City} object.
     *
     * @param name The name of the {@link City}.
     * @param latitude The latitude of the {@link City}, will be {@code 0} if parsing failed.
     * @param longitude The longitude of the {@link City}, will be {@code 0} if parsing failed.
     * @param country The {@link Country} of the {@link City}.
     * @param admin1 The {@link Admin1} of the {@link City}.
     * @param population The population of the {@link City}, will be {@code 0} if parsing failed.
     * @param elevation The elevation of the {@link City}, will be {@code 0} if parsing failed.
     * @return A new {@link City} object.
     */
    public City create(String name, String latitude, String longitude, Country country, Admin1 admin1, String population, String elevation) {
        double _latitude = parseDoubleOrDefault(latitude, 0);
        double _longitude = parseDoubleOrDefault(longitude, 0);
        int _elevation = parseIntOrDefault(elevation, 0);
        long _population = parseLongOrDefault(population, 0);

        return create(name, _latitude, _longitude, country, admin1, _population, _elevation);
    }

    /**
     * Create a {@link City} object.
     *
     * @param name The name of the {@link City}.
     * @param latitude The latitude of the {@link City}.
     * @param longitude The longitude of the {@link City}.
     * @param country The {@link Country} of the {@link City}.
     * @param admin1 The {@link Admin1} of the {@link City}.
     * @param population The population of the {@link City}.
     * @param elevation The elevation of the {@link City}.
     * @return A new {@link City} object.
     */
    public City create(String name, double latitude, double longitude, Country country, Admin1 admin1, long population, int elevation) {
        return new City(name, latitude, longitude, country, admin1, population, elevation);
    }

    /**
     * Try to parse a {@link String} into a {@code double}.
     *
     * @param value The value to be parsed.
     * @param fallback Value to return if there was an error parsing the {@code value}.
     * @return The parsed {@code value}, otherwise the {@code fallback} value.
     */
    private double parseDoubleOrDefault(String value, double fallback) {
        try {
            return Float.parseFloat(value);
        }
        catch (NumberFormatException e) {
            return fallback;
        }
    }

    /**
     * Try to parse a {@link String} into a {@code int}.
     *
     * @param value The value to be parsed.
     * @param fallback Value to return if there was an error parsing the {@code value}.
     * @return The parsed {@code value}, otherwise the {@code fallback} value.
     */
    private int parseIntOrDefault(String value, int fallback) {
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            return fallback;
        }
    }

    /**
     * Try to parse a {@link String} into a {@code long}.
     *
     * @param value The value to be parsed.
     * @param fallback Value to return if there was an error parsing the {@code value}.
     * @return The parsed {@code value}, otherwise the {@code fallback} value.
     */
    private long parseLongOrDefault(String value, long fallback) {
        try {
            return Long.parseLong(value);
        }
        catch (NumberFormatException e) {
            return fallback;
        }
    }

}
