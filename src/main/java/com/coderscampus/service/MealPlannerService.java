package com.coderscampus.service;

import com.coderscampus.domain.DayResponse;
import com.coderscampus.domain.WeekResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;


@Service
public class MealPlannerService {
    @Value("${spoonacular.urls.base}")
    private String spoonacularBaseUrl;

    @Value("${spoonacular.urls.mealplan}")
    private String spoonacularMealplanUrl;

    @Value("${spoonacular.api.key}")
    private String apiKey;


    private final RestTemplate restTemplate = new RestTemplate();

    public DayResponse getDailyMeals(String numCalories, String diet, String exclusions) {

        ResponseEntity<DayResponse> response = restTemplate.getForEntity(buildActualUrl("day", numCalories, diet, exclusions), DayResponse.class);
        return response.getBody();
    }

    public WeekResponse getWeeklyMeals(String numCalories, String diet, String exclusions) {

        ResponseEntity<WeekResponse> response = restTemplate.getForEntity(buildActualUrl("week", numCalories, diet, exclusions), WeekResponse.class);
        return response.getBody();
    }

    public String buildActualUrl(String timeframe, String numCalories, String diet, String exclusions) {
        String apiUrl = spoonacularBaseUrl + spoonacularMealplanUrl;
        String uriString = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("timeFrame", timeframe)
                .queryParam("apiKey", apiKey)
                .queryParamIfPresent("targetCalories", Optional.ofNullable(numCalories))
                .queryParamIfPresent("diet", Optional.ofNullable(diet))
                .queryParamIfPresent("exclusion", Optional.ofNullable(exclusions))
                .build()
                .toUriString();

        System.out.println("Find your meal plan at:" + uriString);
        return uriString;

    }

}


