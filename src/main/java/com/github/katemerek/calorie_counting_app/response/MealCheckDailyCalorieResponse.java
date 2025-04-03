package com.github.katemerek.calorie_counting_app.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealCheckDailyCalorieResponse {

    private int dailyCalorieIntake;

    private int totalCaloriesForDay;

    private int difference;

    private Boolean IsWithinLimit;

    private String statusMessage;
}
