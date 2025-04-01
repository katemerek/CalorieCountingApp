package com.github.katemerek.calorie_counting_app.mapper;

import com.github.katemerek.calorie_counting_app.dto.MealDailyDto;
import com.github.katemerek.calorie_counting_app.dto.MealToAddDto;
import com.github.katemerek.calorie_counting_app.dto.MealHistoryDto;
import com.github.katemerek.calorie_counting_app.model.Dish;
import com.github.katemerek.calorie_counting_app.model.Meal;
import com.github.katemerek.calorie_counting_app.model.Person;
import com.github.katemerek.calorie_counting_app.repository.DishesRepository;
import com.github.katemerek.calorie_counting_app.repository.PeopleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MealMapper {

    private final PeopleRepository peopleRepository;
    private final DishesRepository dishesRepository;

    public MealMapper(PeopleRepository peopleRepository, DishesRepository dishesRepository) {
        this.peopleRepository = peopleRepository;
        this.dishesRepository = dishesRepository;
    }

    public Meal toMeal(MealToAddDto mealToAddDto) {
        Meal meal = new Meal();
        meal.setType(mealToAddDto.getType());
        meal.setDishWeight(mealToAddDto.getDishWeight());
        meal.setDate(mealToAddDto.getDate());

        Person person = peopleRepository.findById(mealToAddDto.getPersonId())
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + mealToAddDto.getPersonId()));
        meal.setPerson(person);

        Dish dish = dishesRepository.findById(mealToAddDto.getDishId())
                .orElseThrow(() -> new EntityNotFoundException("Dish not found with id: " + mealToAddDto.getDishId()));
        meal.setDish(dish);

        return meal;
    }

    public MealDailyDto toMealDto(Meal meal) {
        MealDailyDto mealDailyDto = new MealDailyDto();
        mealDailyDto.setId(meal.getMeal_id());
        mealDailyDto.setType(meal.getType());
        mealDailyDto.setDishName(meal.getDish().getDish_name());
        mealDailyDto.setDishWeight(meal.getDishWeight());
        mealDailyDto.setDishCalories(meal.getDishCalories());
        return mealDailyDto;
    }

    public MealHistoryDto toMealDto3(Meal meal) {
        MealHistoryDto mealHistoryDto = new MealHistoryDto();
        mealHistoryDto.setId(meal.getMeal_id());
        mealHistoryDto.setDate(meal.getDate());
        mealHistoryDto.setType(meal.getType());
        mealHistoryDto.setDishName(meal.getDish().getDish_name());
        mealHistoryDto.setDishWeight(meal.getDishWeight());
        mealHistoryDto.setDishCalories(meal.getDishCalories());
        return mealHistoryDto;
    }
}
