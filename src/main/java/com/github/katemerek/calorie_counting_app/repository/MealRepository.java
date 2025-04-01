package com.github.katemerek.calorie_counting_app.repository;

import com.github.katemerek.calorie_counting_app.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Integer> {
    List<Meal> findAll();

    @Query("SELECT m FROM Meal m JOIN FETCH m.dish WHERE m.person.id = :personId AND m.date = :date")
    List<Meal> findByPersonIdAndDateWithDish(@Param("personId") int personId,
                                             @Param("date") LocalDate date);

    List<Meal> findByPersonIdOrderByTypeAsc(int personId);
}
