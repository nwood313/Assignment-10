package com.coderscampus.web;

import com.coderscampus.domain.DayResponse;
import com.coderscampus.domain.WeekResponse;
import com.coderscampus.service.MealPlannerService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MealPlannerController {

    private final MealPlannerService mealPlannerService;

    public MealPlannerController(MealPlannerService mealPlannerService) {
        this.mealPlannerService = mealPlannerService;
    }

    @GetMapping("mealplanner/week")
    public ResponseEntity<WeekResponse> getWeekMeals(@RequestParam (required = false) String numCalories,
                                                     @RequestParam (required = false) String diet,
                                                     @RequestParam (required = false) String exclusions) {
        WeekResponse weekResponse = mealPlannerService.getWeeklyMeals(numCalories, diet, exclusions);
        return ResponseEntity.ok(weekResponse);
    }

    @GetMapping("mealplanner/day")
    public ResponseEntity<DayResponse> getDayMeals(@RequestParam (required = false) String numCalories,
                                                   @RequestParam (required = false) String diet,
                                                   @RequestParam (required = false) String exclusions) {
        DayResponse dayResponse = mealPlannerService.getDailyMeals(numCalories, diet, exclusions);
        return ResponseEntity.ok(dayResponse);
    }
}

//(defaultValue = "")