package com.github.katemerek.calorie_counting_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class MealDailyResponse {

    private LocalDate date;

    private int personId;

    private List<MealDailyDto> mealsDailyDto;

    private int TotalCaloriesForDay;
}
