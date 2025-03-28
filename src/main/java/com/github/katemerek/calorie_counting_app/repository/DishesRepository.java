package com.github.katemerek.calorie_counting_app.repository;

import com.github.katemerek.calorie_counting_app.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishesRepository extends JpaRepository<Dish, Integer> {
    List<Dish> findAll();
}
