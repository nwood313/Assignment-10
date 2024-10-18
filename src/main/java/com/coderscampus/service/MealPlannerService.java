package com.coderscampus.service;

import com.coderscampus.domain.DayResponse;
import com.coderscampus.domain.WeekResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class MealPlannerService {
    @Value("${spoonacular.urls.base}")
    private String spoonacularBaseUrl;

    @Value("${spoonacular.urls.mealplan}")
    private String spoonacularMealplanUrl;

    @Value("${spoonacular.api.key}")
    private String apiKey;


    public WeekResponse getWeeklyMeals(String numCalories, String diet, String exclusions) {
        String apiUrl = spoonacularBaseUrl + spoonacularMealplanUrl;
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("timeFrame", "week")
                .queryParam("targetCalories", numCalories)
                .queryParam("diet", diet)
                .queryParam("exclude", exclusions)
                .queryParam("apiKey", apiKey);

        WeekResponse weekResponse = new WeekResponse();
        weekResponse.setDiet(diet);
        weekResponse.setNumCalories(numCalories);
        weekResponse.setExclusions(exclusions);
        return weekResponse;
    }

    public DayResponse getDailyMeals(String numCalories, String diet, String exclusions) {
        String apiUrl = spoonacularBaseUrl + spoonacularMealplanUrl;
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("timeFrame", "day")
                .queryParam("targetCalories", numCalories)
                .queryParam("diet", diet)
                .queryParam("exclude", exclusions)
                .queryParam("apiKey", apiKey);

        DayResponse dayResponse = new DayResponse();
        dayResponse.setDiet(diet);
        dayResponse.setNumCalories(numCalories);
        dayResponse.setExclusions(exclusions);
        return dayResponse;
    }
}

