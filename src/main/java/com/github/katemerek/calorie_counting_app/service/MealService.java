package com.github.katemerek.calorie_counting_app.service;

import com.github.katemerek.calorie_counting_app.dto.*;
import com.github.katemerek.calorie_counting_app.mapper.MealMapper;
import com.github.katemerek.calorie_counting_app.model.Meal;
import com.github.katemerek.calorie_counting_app.model.Person;
import com.github.katemerek.calorie_counting_app.repository.DishesRepository;
import com.github.katemerek.calorie_counting_app.repository.MealRepository;
import com.github.katemerek.calorie_counting_app.repository.PeopleRepository;
import com.github.katemerek.calorie_counting_app.response.MealCheckDailyCalorieResponse;
import com.github.katemerek.calorie_counting_app.response.MealDailyResponse;
import com.github.katemerek.calorie_counting_app.response.MealHistoryResponse;
import com.github.katemerek.calorie_counting_app.util.DateNotFoundException;
import com.github.katemerek.calorie_counting_app.util.DishNotFoundException;
import com.github.katemerek.calorie_counting_app.util.PersonNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MealService {
    private final MealRepository mealRepository;
    private final PeopleRepository peopleRepository;
    private final DishesRepository dishesRepository;
    private final MealMapper mealMapper;

    public List<Meal> getAllMeal() {
        return mealRepository.findAll();
    }

    @Transactional
    public int save(Meal meal) throws PersonNotFoundException, DishNotFoundException {
        if (!peopleRepository.existsById(meal.getPerson().getId())) {
            throw new PersonNotFoundException(meal.getPerson().getId());
        }
        if (!dishesRepository.existsById(meal.getDish().getId())) {
            throw new DishNotFoundException(meal.getDish().getId());
        }
        {
            supplementMeal(meal);
            mealRepository.save(meal);
            return meal.getMeal_id();
        }
    }

    public void supplementMeal(Meal meal) {
        meal.setDishCalories(Math.round((meal.getDishWeight() / 100) * meal.getDish().getCalories()));
    }

    public MealDailyResponse getDailyMeals(int personId, LocalDate date) throws PersonNotFoundException, DateNotFoundException {
        if (!peopleRepository.existsById(personId)) {
            throw new PersonNotFoundException(personId);
        }
        if (!mealRepository.existsByDate(date)) {
            throw new DateNotFoundException("Not Found Meal for this date");
        }
            List<Meal> meals = mealRepository.findByPersonIdAndDateWithDish(personId, date);
            List<MealDailyDto> mealsDailyDto = meals.stream()
                    .map(mealMapper::toMealDto)
                    .toList();
            int TotalCaloriesForDay = calculateTotalCaloriesForDay(meals);
            return new MealDailyResponse(
                    date,
                    personId,
                    mealsDailyDto,
                    TotalCaloriesForDay
            );
    }

    public int calculateTotalCaloriesForDay(List<Meal> meals) {
        int totalCalories = 0;
        for (Meal meal : meals) {
            totalCalories += (int) meal.getDishCalories();
        }
        return totalCalories;
    }

    public MealHistoryResponse getFoodHistory(int personId) throws PersonNotFoundException {
        if (!peopleRepository.existsById(personId)) {
            throw new PersonNotFoundException(personId);
        }
        List<Meal> meals = mealRepository.findByPersonIdOrderByDateAsc(personId);
        List<MealHistoryDto> mealsHistoryDto = meals.stream()
                .map(mealMapper::toMealDto3)
                .toList();
        return new MealHistoryResponse(
                personId,
                mealsHistoryDto
        );
    }

    public MealCheckDailyCalorieResponse checkDailyCalorieIntake(int personId, LocalDate date) throws PersonNotFoundException, DateNotFoundException {
        Person person = peopleRepository.findById(personId)
                .orElseThrow(() -> new PersonNotFoundException(personId));
        if (!mealRepository.existsByDate(date)) {
            throw new DateNotFoundException("Not Found Meal for this date");
        }
        List<Meal> meals = mealRepository.findByPersonIdAndDateWithDish(personId, date);
        int totalCaloriesForDay = calculateTotalCaloriesForDay(meals);

        int dailyCalorieIntake = person.getDailyCalorieIntake();

        int difference = dailyCalorieIntake - totalCaloriesForDay;

        MealCheckDailyCalorieResponse response = new MealCheckDailyCalorieResponse();
        response.setDailyCalorieIntake(dailyCalorieIntake);
        response.setTotalCaloriesForDay(totalCaloriesForDay);
        response.setDifference(difference);
        response.setIsWithinLimit(difference >= 0);

        if (difference > 0) {
            response.setStatusMessage("Вы уложились в норму! Осталось " + difference + " ккал");
        } else if (difference == 0) {
            response.setStatusMessage("Вы достигли точной дневной нормы!");
        } else {
            response.setStatusMessage("Превышение нормы на " + Math.abs(difference) + " ккал");
        }
        return response;
    }
}
    
