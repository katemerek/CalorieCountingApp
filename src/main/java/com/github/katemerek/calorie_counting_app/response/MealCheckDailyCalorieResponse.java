package com.github.katemerek.calorie_counting_app.response;

import lombok.Data;

@Data
public class MealCheckDailyCalorieResponse {

    private int dailyCalorieIntake;

    private int totalCaloriesForDay;

    private int difference;

    private Boolean IsWithinLimit;

    private String statusMessage;
}
