package com.matthewteolis.coveo.challenge.citysuggester.dto;

import com.matthewteolis.coveo.challenge.citysuggester.entity.suggestion.Suggestion;

import java.util.List;

/**
 * DTO object for serializing the object to a JSON string.
 * Used as a HTTP response object.
 */
public class SuggestionsDTO {

    private List<Suggestion> suggestions;

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

}
