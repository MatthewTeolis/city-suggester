package com.matthewteolis.coveo.challenge.citysuggester.entity.country;

import org.springframework.stereotype.Component;

@Component
public class CountryFactory {

    /**
     * Create a {@link Country} object.
     *
     * @param ISO The representation of the {@link Country} code, in ISO format.
     * @param name The name of the {@link Country}.
     * @return A new {@link Country} object.
     */
    public Country create(String ISO, String name) {
        return new Country(ISO, name);
    }

}
