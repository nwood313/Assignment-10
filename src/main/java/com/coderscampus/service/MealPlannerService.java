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

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ResponseEntity<WeekResponse> getWeeklyMeals(@RequestParam String numCalories, @RequestParam String diet, @RequestParam String exclusions) throws JsonProcessingException {
        String apiUrl = spoonacularBaseUrl + spoonacularMealplanUrl;
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("timeFrame", "week")
                .queryParam("targetCalories", numCalories)
                .queryParam("diet", diet)
                .queryParam("exclude", exclusions)
                .queryParam("apiKey", apiKey);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(builder
                .build()
                .toUri(), HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String responseBody = response.getBody();
            WeekResponse weekResponse = objectMapper.readValue(responseBody, WeekResponse.class);
            return ResponseEntity.ok(weekResponse);
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(null);
        }
    }

    public ResponseEntity<DayResponse> getDailyMeals(@RequestParam String numCalories, @RequestParam String diet, @RequestParam String exclusions) {
        String apiUrl = spoonacularBaseUrl + spoonacularMealplanUrl;
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("timeFrame", "day")
                .queryParam("targetCalories", numCalories)
                .queryParam("diet", diet)
                .queryParam("exclude", exclusions)
                .queryParam("apiKey", apiKey);

        String uri = builder.toUriString();
        try {
            ResponseEntity<DayResponse> response = restTemplate.getForEntity(uri, DayResponse.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok(response.getBody());
            } else {
                return ResponseEntity.status(response.getStatusCode()).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}        //        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<String> entity = new HttpEntity<>(headers);

//        ResponseEntity<String> response = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, entity, String.class);
//
//        if (response.getStatusCode() == HttpStatus.OK) {
//            String responseBody = response.getBody();
//            WeekResponse weekResponse = objectMapper.readValue(responseBody, WeekResponse.class);
//            return ResponseEntity.ok(weekResponse);
//        } else {
//            return ResponseEntity.status(response.getStatusCode()).body(null);
//        }


