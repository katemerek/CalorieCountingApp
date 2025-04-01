package com.github.katemerek.calorie_counting_app.model;

import com.github.katemerek.calorie_counting_app.enumiration.Gender;
import com.github.katemerek.calorie_counting_app.enumiration.Goal;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Table(name="Person")
@Getter
@Setter
@ToString(exclude = {"meals"})
@NoArgsConstructor
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    @NotBlank(message = "Please enter your username")
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Gender gender;

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

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private Goal goal;

    @Column(name = "daily_calorie_intake")
    private int dailyCalorieIntake;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("type")
    private List<Meal> meals;
}
