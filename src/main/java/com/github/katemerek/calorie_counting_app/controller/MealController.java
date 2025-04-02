package com.github.katemerek.calorie_counting_app.controller;

import com.github.katemerek.calorie_counting_app.dto.*;
import com.github.katemerek.calorie_counting_app.mapper.MealMapper;
import com.github.katemerek.calorie_counting_app.model.Meal;
import com.github.katemerek.calorie_counting_app.service.MealService;
import com.github.katemerek.calorie_counting_app.util.DateNotFoundException;
import com.github.katemerek.calorie_counting_app.util.DishNotFoundException;
import com.github.katemerek.calorie_counting_app.util.PersonNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meal")
public class MealController {
    private final MealService mealService;
    private final MealMapper mealMapper;

    @GetMapping
    public List<MealDailyDto> getAllMeals() {
        return mealService.getAllMeal()
                .stream()
                .map(mealMapper::toMealDto)
                .toList();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeal (@RequestBody @Valid MealToAddDto mealToAddDto) throws PersonNotFoundException, DishNotFoundException {
        Meal mealToAdd = mealMapper.toMeal(mealToAddDto);
        mealService.save(mealToAdd);
        return ResponseEntity.ok(HttpStatus.CREATED);

    }

    @GetMapping("/daily")
    public MealDailyResponse getDailyMeals(@RequestParam int personId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
            throws PersonNotFoundException, DateNotFoundException {
        return mealService.getDailyMeals(personId, date);
    }

    @GetMapping("/history")
    public MealHistoryResponse getFoodHistoryByDay(@RequestParam int personId) throws PersonNotFoundException {
        return mealService.getFoodHistory(personId);
    }

    @GetMapping("/daily-check")
    public MealCheckDailyCalorieResponse checkDailyCalorieIntake(@RequestParam int personId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
            throws PersonNotFoundException, DateNotFoundException {
        return mealService.checkDailyCalorieIntake(personId, date);
    }
}
