package com.github.katemerek.calorie_counting_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealDailyDto {

    private int id;

    @NotBlank(message = "The type of meal must be specified: TypeOfMeal.BREAKFAST, TypeOfMeal.LUNCH, TypeOfMeal.DINNER, TypeOfMeal.ANOTHER")
    private String type;

    private String dishName;

    @NotNull
    private double dishWeight;

    private double dishCalories;
}
