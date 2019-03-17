package com.matthewteolis.coveo.challenge.citysuggester.controller;

import com.matthewteolis.coveo.challenge.citysuggester.dto.SuggestionsDTO;
import com.matthewteolis.coveo.challenge.citysuggester.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/suggestions")
public class SuggestionController {

    private final SuggestionService suggestionService;

    @Autowired
    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @GetMapping
    public SuggestionsDTO getSuggestions(
            @RequestParam("q") String query,
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude
    ) {
        final SuggestionsDTO suggestionsDTO = new SuggestionsDTO();

        suggestionsDTO.setSuggestions(suggestionService.computeSuggestions(query, latitude, longitude));

        return suggestionsDTO;
    }

}
