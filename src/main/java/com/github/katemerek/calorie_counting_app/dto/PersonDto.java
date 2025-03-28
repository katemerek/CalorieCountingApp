package com.github.katemerek.calorie_counting_app.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {
    private String username;

    @Email(message = "Please provide a valid email address")
    private String email;

    @Min(value = 18, message = "Please enter your age between 18 and 90 years old")
    @Max(value = 90, message = "Please enter your age between 18 and 90 years old")
    private int age;

    @Min(value = 50, message = "Please enter your weight between 50 and 200 kilograms")
    @Max(value = 200, message = "Please enter your weight between 50 and 200 kilograms")
    private int weight;

    @Min(value = 140, message = "Please enter your height between 140 and 230 centimeters")
    @Max(value = 230, message = "Please enter your height between 140 and 230 centimeters")
    private int height;

    private String goal;
}
