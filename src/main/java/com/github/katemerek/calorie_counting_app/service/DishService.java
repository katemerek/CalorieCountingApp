package com.github.katemerek.calorie_counting_app.service;

import com.github.katemerek.calorie_counting_app.model.Dish;
import com.github.katemerek.calorie_counting_app.repository.DishesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DishService {
    private final DishesRepository dishesRepository;

    public List<Dish> getAllDishes() {
        return dishesRepository.findAll();
    }

    @Transactional
    public void save(Dish dish) {
        dishesRepository.save(dish);
    }
}
