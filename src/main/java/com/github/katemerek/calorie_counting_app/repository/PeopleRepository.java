package com.github.katemerek.calorie_counting_app.repository;

import com.github.katemerek.calorie_counting_app.model.Meal;
import com.github.katemerek.calorie_counting_app.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    List<Person> findAll();

    Optional<Person> findById(int id);
}
