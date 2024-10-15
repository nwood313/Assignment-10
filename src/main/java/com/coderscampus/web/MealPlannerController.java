package com.coderscampus.web;

import com.coderscampus.model.DayResponse;
import com.coderscampus.model.WeekResponse;
import com.coderscampus.repository.SpoonacularApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class MealPlannerController {

// the controller will have the following endpoints, will be a rep of the JSON Strings
//
// (likely need a repository)
//API Key: 7714e04bdfe945429e3e5f6ca1ace8d9
    @Autowired
    private final WeekResponse weekResponse;
    @Autowired
    DayResponse dayResponse;


    private final SpoonacularApiRepository spoonacularApiRepository;

    public MealPlannerController(SpoonacularApiRepository spoonacularApiRepository) {
        this.spoonacularApiRepository = spoonacularApiRepository;
    }

    @GetMapping("mealplanner/week")
    public ResponseEntity<WeekResponse> getWeekMeals(@RequestParam String numCalories, @RequestParam String diet, @RequestParam String exclusions) {
        WeekResponse weekResponse = spoonacularApiRepository.getWeekMeals(numCalories, diet, exclusions);
        return ResponseEntity.ok(weekResponse);
    }

    @GetMapping("mealplanner/day")
    public ResponseEntity<DayResponse> getDayMeals(@RequestParam String numCalories, @RequestParam String diet, @RequestParam String exclusions) {
        DayResponse dayResponse = spoonacularApiRepository.getDayMeals(numCalories, diet, exclusions);
        return ResponseEntity.ok(dayResponse);
    }
    @Value("${spoonacular.urls.base}")
    private String spoonacularBaseUrl;

    @Value("${spoonacular.urls.mealplan}")
    private String spoonacularMealplanUrl;

    @Value("${spoonacular.api.key}")
    private String spoonacularApiKey;

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("mealplanner/week")
    public ResponseEntity<WeekResponse> getWeekMeals(@RequestParam String numCalories, @RequestParam String diet, @RequestParam String exclusions) {
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
            WeekResponse weekResponse = objectMapper.readValue(responseBody, WeekResponse.class);
            return ResponseEntity.ok(weekResponse);
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(null);
        }

    }


}
