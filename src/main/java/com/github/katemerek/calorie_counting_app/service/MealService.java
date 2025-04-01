package com.github.katemerek.calorie_counting_app.service;

import com.github.katemerek.calorie_counting_app.dto.*;
import com.github.katemerek.calorie_counting_app.mapper.MealMapper;
import com.github.katemerek.calorie_counting_app.model.Meal;
import com.github.katemerek.calorie_counting_app.model.Person;
import com.github.katemerek.calorie_counting_app.repository.MealRepository;
import com.github.katemerek.calorie_counting_app.repository.PeopleRepository;
import com.github.katemerek.calorie_counting_app.util.InvalidDataException;
import com.github.katemerek.calorie_counting_app.util.PersonNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MealService {
    private final MealRepository mealRepository;
    private final PeopleRepository peopleRepository;
    private final MealMapper mealMapper;

    public List<Meal> getAllMeal() {
        return mealRepository.findAll();
    }

    @Transactional
    public void save(Meal meal) throws PersonNotFoundException {
        if (!peopleRepository.existsById(meal.getPerson().getId())) {
            throw new PersonNotFoundException(meal.getPerson().getId());
        }
        {
            supplementMeal(meal);
            mealRepository.save(meal);
        }
    }

    public void supplementMeal(Meal meal) {
        meal.setDishCalories(Math.round((meal.getDishWeight() / 100) * meal.getDish().getCalories()));
    }

    public MealDailyResponse getDailyMeals(int personId, LocalDate date) throws PersonNotFoundException {
        if (!peopleRepository.existsById(personId)) {
            throw new PersonNotFoundException(personId);
        }
        {
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
    }

    public int calculateTotalCaloriesForDay(List<Meal> meals) {
        int totalCalories = 0;
        for (Meal meal : meals) {
            totalCalories += (int) meal.getDishCalories();
        }
        return totalCalories;
    }

    public MealHistoryResponse getFoodHistoryByDay(int personId) {
        List<Meal> meals = mealRepository.findByPersonIdOrderByTypeAsc(personId);
        List<MealHistoryDto> mealsHistoryDto = meals.stream()
                .map(mealMapper::toMealDto3)
                .toList();
        return new MealHistoryResponse(
                personId,
                mealsHistoryDto
        );
    }

    public MealCheckDailyCalorieResponse checkDailyCalorieIntake(int personId, LocalDate date) {
        Person person = peopleRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));
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
    
