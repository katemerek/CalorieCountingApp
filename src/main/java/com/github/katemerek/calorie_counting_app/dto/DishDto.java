package com.github.katemerek.calorie_counting_app.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishDto {

    @NotBlank(message = "Please enter the name of the dish")
    private String dish_name;

    @NotNull(message = "Please enter the calorie content of the dish")
    private double calories;

    private double proteins;

    private double fats;

    private double carbohydrates;
}
