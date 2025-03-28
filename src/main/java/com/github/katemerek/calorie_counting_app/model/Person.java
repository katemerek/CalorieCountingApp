package com.github.katemerek.calorie_counting_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Person")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "sex")
    private String sex;


    @Email(message = "Please provide a valid email address")
    @Column(name = "email")
    private String email;

    @Min(value = 18, message = "Please enter your age between 18 and 90 years old")
    @Max(value = 90, message = "Please enter your age between 18 and 90 years old")
    @Column(name = "age")
    private int age;

    @Min(value = 50, message = "Please enter your weight between 50 and 200 kilograms")
    @Max(value = 200, message = "Please enter your weight between 50 and 200 kilograms")
    @Column(name = "weight")
    private int weight;

    @Min(value = 140, message = "Please enter your height between 140 and 230 centimeters")
    @Max(value = 230, message = "Please enter your height between 140 and 230 centimeters")
    @Column(name = "height")
    private int height;

    @Column(name = "goal")
    private String goal;

    @Column(name = "daily_calorie_intake")
    private String dailyCalorieIntake;
}
