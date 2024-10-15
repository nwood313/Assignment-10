package com.coderscampus.repository;

import com.coderscampus.model.WeekResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class SpoonacularApiRepository {

    @Value("${spoonacular.urls.base}")
    private String spoonacularBaseUrl;

    @Value("${spoonacular.urls.mealplan}")
    private String spoonacularMealplanUrl;

    @Value("${spoonacular.api.key}")
    private String spoonacularApiKey;

    private RestTemplate restTemplate = new RestTemplate();

    public WeekResponse getWeekMeals(String numCalories, String diet, String exclusions) {
        String apiUrl = spoonacularBaseUrl + spoonacularMealplanUrl;
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("timeFrame", "week")
                .queryParam("targetCalories", numCalories)
                .queryParam("diet", diet)
                .queryParam("exclude", exclusions)
                .queryParam("apiKey", spoonacularApiKey);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String responseBody = response.getBody();
            return objectMapper.readValue(responseBody, WeekResponse.class);
        } else {
            throw new RuntimeException("Error calling Spoonacular API");
        }
    }
}