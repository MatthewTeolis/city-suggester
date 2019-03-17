package com.matthewteolis.coveo.challenge.citysuggester.entity.suggestion;

import com.matthewteolis.coveo.challenge.citysuggester.entity.city.City;
import org.springframework.stereotype.Component;

@Component
public class SuggestionFactory {

    /**
     * Create a {@link Suggestion} object.
     *
     * @param city The {@link City} object attached to the {@link Suggestion}.
     * @param score The confidence value for the {@link Suggestion}.
     * @return A new {@link Suggestion} object.
     */
    public Suggestion create(City city, double score) {
        return new Suggestion(city, score);
    }

}
