package com.github.katemerek.calorie_counting_app.model;

import com.github.katemerek.calorie_counting_app.enumiration.TypeOfMeal;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "meal")
@Getter
@Setter
@ToString(exclude = {"person", "dish"})
@NoArgsConstructor
public class Meal {
    @Id
    @Column(name = "meal_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int meal_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name = "date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private TypeOfMeal type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @Column(name = "dish_weight")
    @NotNull
    private double dishWeight;

    @Column(name = "dish_calories")
    private double dishCalories;
}
