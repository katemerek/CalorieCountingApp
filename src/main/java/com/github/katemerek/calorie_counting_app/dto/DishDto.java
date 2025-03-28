package com.github.katemerek.calorie_counting_app.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishDto {

    @NotNull
    private String dish_name;

    @NotNull
    private String calories_per_serving;

    @NotNull
    private int proteins_fats_carbohydrates;
}
