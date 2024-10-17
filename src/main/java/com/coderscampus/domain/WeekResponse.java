package com.coderscampus.domain;

public class WeekResponse {
    private String numCalories;
    private String diet;
    private String exclusions;

    public WeekResponse() {

    }

    public String getNumCalories() {
        return numCalories;
    }

    public void setNumCalories(String numCalories) {
        this.numCalories = numCalories;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getExclusions() {
        return exclusions;
    }

    public void setExclusions(String exclusions) {
        this.exclusions = exclusions;
    }

    @Override
    public String toString() {
        return "WeekResponse{" +
                "numCalories='" + numCalories + '\'' +
                ", diet='" + diet + '\'' +
                ", exclusions='" + exclusions + '\'' +
                '}';
    }
}
