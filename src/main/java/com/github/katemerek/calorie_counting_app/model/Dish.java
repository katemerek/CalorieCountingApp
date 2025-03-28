package com.github.katemerek.calorie_counting_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "dishes")
public class Dish {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "dish_name")
    @NotNull
    private String dish_name;

    @Column(name = "calories_per_serving")
    @NotNull
    private String calories_per_serving;

    @Column(name = "proteins_fats_carbohydrates")
    @NotNull
    private int proteins_fats_carbohydrates;
}
