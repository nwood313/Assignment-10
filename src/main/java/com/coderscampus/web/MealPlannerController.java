package com.coderscampus.web;

import com.coderscampus.domain.DayResponse;
import com.coderscampus.domain.WeekResponse;
import com.coderscampus.service.MealPlannerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MealPlannerController {

    private final MealPlannerService mealPlannerService;

    // Removed WeekResponse and DayResponse from constructor parameters
    public MealPlannerController(MealPlannerService mealPlannerService) {
        this.mealPlannerService = mealPlannerService;
    }

    @GetMapping("mealplanner/week")
    public ResponseEntity<WeekResponse> getWeekMeals(@RequestParam String numCalories, @RequestParam String diet, @RequestParam String exclusions) throws JsonProcessingException {
        WeekResponse weekResponse = mealPlannerService.getWeeklyMeals(numCalories, diet, exclusions).getBody();
        return ResponseEntity.ok(weekResponse);
    }

    @GetMapping("mealplanner/day")
    public ResponseEntity<DayResponse> getDayMeals(@RequestParam String numCalories, @RequestParam String diet, @RequestParam String exclusions) {
        DayResponse dayResponse = mealPlannerService.getDailyMeals(numCalories, diet, exclusions).getBody();
        return ResponseEntity.ok(dayResponse);
    }
}