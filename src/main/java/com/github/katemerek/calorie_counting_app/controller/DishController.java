package com.github.katemerek.calorie_counting_app.controller;

import com.github.katemerek.calorie_counting_app.dto.DishDto;
import com.github.katemerek.calorie_counting_app.dto.PersonDto;
import com.github.katemerek.calorie_counting_app.mapper.DishMapper;
import com.github.katemerek.calorie_counting_app.model.Dish;
import com.github.katemerek.calorie_counting_app.model.Person;
import com.github.katemerek.calorie_counting_app.service.DishService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dish")
public class DishController {
    private final DishService dishService;
    private final DishMapper dishMapper;

    @GetMapping
    public List<DishDto> getAllDishes() {
        return dishService.getAllDishes()
                .stream()
                .map(dishMapper::toDishDto)
                .toList();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addDish(@RequestBody @Valid DishDto dishDto) {
        Dish dishToAdd = dishMapper.toDish(dishDto);
        dishService.save(dishToAdd);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
