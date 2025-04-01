package com.github.katemerek.calorie_counting_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "dish")
@Getter
@Setter
@ToString(exclude = {"meals"})
@NoArgsConstructor
public class Dish {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "dish_name")
    @NotBlank(message = "Please enter the name of the dish")
    private String dish_name;

    @Column(name = "calories")
    @NotNull(message = "Please enter the calorie content of the dish")
    private double calories;

    @Column(name = "proteins")
    private double proteins;

    @Column(name = "fats")
    private double fats;

    @Column(name = "carbohydrates")
    private double carbohydrates;

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Meal> meals;
}
