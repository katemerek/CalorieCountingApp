package com.github.katemerek.calorie_counting_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "meal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meal {
    @Id
    @Column(name = "meal_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int meal_id;

    @Column(name = "person_id")
    private int person_id;

    @Column(name = "day_of_weeks")
    private String dayOfTheWeek;

    @Column(name = "type_of_meal")
    private int typeOfMeal;

    @Column(name = "dish_id")
    private int dishes_id;

    @Column(name = "dish_name")
    private String dish_name;

    @Column(name = "dish_weight")
    private int dish_weight;

    @Column(name = "dish_calories")
    private int dish_calories;

    @Column(name = "total_calories")
    private int total_calories;

    @Column(name="calorie_requirement")
    private boolean calorie_requirement;

}
