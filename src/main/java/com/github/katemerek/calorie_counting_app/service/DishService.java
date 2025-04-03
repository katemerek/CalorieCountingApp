package com.github.katemerek.calorie_counting_app.service;

import com.github.katemerek.calorie_counting_app.model.Dish;
import com.github.katemerek.calorie_counting_app.repository.DishesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DishService {
    private final DishesRepository dishesRepository;

    public List<Dish> getAllDishes() {
        return dishesRepository.findAll();
    }

    @Transactional
    public int save(Dish dish) {
        dishesRepository.save(dish);
        return dish.getId();
    }
}
