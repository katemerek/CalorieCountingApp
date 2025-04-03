package com.github.katemerek.calorie_counting_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.katemerek.calorie_counting_app.dto.MealDailyDto;
import com.github.katemerek.calorie_counting_app.dto.MealHistoryDto;
import com.github.katemerek.calorie_counting_app.dto.MealToAddDto;
import com.github.katemerek.calorie_counting_app.mapper.MealMapper;
import com.github.katemerek.calorie_counting_app.model.Meal;
import com.github.katemerek.calorie_counting_app.response.MealCheckDailyCalorieResponse;
import com.github.katemerek.calorie_counting_app.response.MealDailyResponse;
import com.github.katemerek.calorie_counting_app.response.MealHistoryResponse;
import com.github.katemerek.calorie_counting_app.service.MealService;
import com.github.katemerek.calorie_counting_app.util.DateNotFoundException;
import com.github.katemerek.calorie_counting_app.util.PersonNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static com.github.katemerek.calorie_counting_app.enumiration.TypeOfMeal.BREAKFAST;
import static com.github.katemerek.calorie_counting_app.enumiration.TypeOfMeal.LUNCH;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MealController.class)
public class MealControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private MealService mealService;

    @Autowired
    ObjectMapper mapper;

    @MockitoBean
    private MealMapper mealMapper;

    private final LocalDate testDate = LocalDate.of(2025, 3, 1);
    LocalDate dateOld = LocalDate.of(1900, 1, 1);
    private final int personId = 1;

    private MealToAddDto createMealToAddDto() {
        MealToAddDto mealToAddDto = new MealToAddDto();
        mealToAddDto.setDate(LocalDate.of(2025, 3, 1));
        mealToAddDto.setPersonId(1);
        mealToAddDto.setType(BREAKFAST.name());
        mealToAddDto.setDishId(5);
        mealToAddDto.setDishWeight(300);
        mealToAddDto.setDishCalories(450);
        return mealToAddDto;
    }

    private Meal createMeal() {
        Meal meal = new Meal();
        meal.setMeal_id(3);
        meal.setDate(LocalDate.of(2025, 3, 1));
        meal.setType(BREAKFAST);
        meal.setDishWeight(300);
        meal.setDishCalories(450);
        return meal;
    }

    private List<Meal> getMeals() {
        Meal one = new Meal();
        Meal two = new Meal();
        return List.of(one, two);
    }

    private List<MealDailyDto> getMealDailyDtos() {
        MealDailyDto one = new MealDailyDto();
        one.setId(3);
        one.setType(BREAKFAST.name());
        one.setDishName("Milk");
        one.setDishWeight(200);
        one.setDishCalories(150);

        MealDailyDto two = new MealDailyDto();
        two.setId(4);
        two.setType(LUNCH.name());
        two.setDishName("Coffee");
        two.setDishWeight(100);
        two.setDishCalories(80);
        return List.of(one, two);
    }

    private List<MealHistoryDto> getMealHistoryDtos() {
        MealHistoryDto one = new MealHistoryDto();
        one.setId(3);
        one.setDate(testDate);
        one.setType(BREAKFAST.name());
        one.setDishName("Milk");
        one.setDishWeight(200);
        one.setDishCalories(150);

        MealHistoryDto two = new MealHistoryDto();
        two.setId(4);
        two.setDate(testDate);
        two.setType(LUNCH.name());
        two.setDishName("Coffee");
        two.setDishWeight(100);
        two.setDishCalories(80);
        return List.of(one, two);
    }


    @Test
    void getAllMeals_ShouldReturnListOfMealDtos() throws Exception {
        when(mealService.getAllMeal()).thenReturn(getMeals());

        mvc.perform(get("/meal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
        verify(mealService).getAllMeal();
    }

    @Test
    void addMeal_ShouldReturnCreatedStatus() throws Exception {
        when(mealMapper.toMeal(any(MealToAddDto.class))).thenReturn(createMeal());
        when(mealService.save(any(Meal.class))).thenReturn(createMeal().getMeal_id());

        mvc.perform(post("/meal/add")
                        .content(mapper.writeValueAsString(createMealToAddDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3));
        verify(mealService).save(any(Meal.class));
        verify(mealMapper).toMeal(any(MealToAddDto.class));
    }

    @Test
    void addMeal_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        MealToAddDto invalidDto = new MealToAddDto();
        invalidDto.setType("");

        mvc.perform(post("/meal/add")
                        .content(mapper.writeValueAsString(invalidDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getDailyMeals_shouldReturnDailyMealsForValidPersonAndDate() throws Exception {
        MealDailyResponse expectedResponse = new MealDailyResponse(testDate, personId, getMealDailyDtos(), 1800);
        when(mealService.getDailyMeals(personId, testDate)).thenReturn(expectedResponse);

        mvc.perform(get("/meal/daily")
                        .param("personId", String.valueOf(personId))
                        .param("date", testDate.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.date").value(testDate.toString()))
                .andExpect(jsonPath("$.personId").value(personId));
        verify(mealService).getDailyMeals(personId, testDate);

    }

    @Test
    void getDailyMeals_shouldThrowPersonNotFoundException() throws Exception {
        int personId = 1000;
        when(mealService.getDailyMeals(personId, testDate)).thenThrow(new PersonNotFoundException(1000));

        mvc.perform(get("/meal/daily")
                        .param("personId", "1000")
                        .param("date", LocalDate.now().toString()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Person not found with id: 1000"));

    }

    @Test
    void getDailyMeals_shouldThrowDateNotFoundException() throws Exception {
        when(mealService.getDailyMeals(personId, dateOld)).thenThrow(new DateNotFoundException("Date not found"));

        mvc.perform(get("/meal/daily")
                        .param("personId", String.valueOf(personId))
                        .param("date", String.valueOf(dateOld)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Date not found"));

    }

    @Test
    void getFoodHistoryByDay_shouldReturnHistoryForValidPerson() throws Exception {
        MealHistoryResponse expectedResponse = new MealHistoryResponse(personId, getMealHistoryDtos());
        when(mealService.getFoodHistory(personId)).thenReturn(expectedResponse);

        mvc.perform(get("/meal/history")
                        .param("personId", String.valueOf(personId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.personId").value(personId));
        verify(mealService).getFoodHistory(personId);
    }

    @Test
    void getFoodHistoryByDay_shouldThrowPersonNotFoundException() throws Exception {
        int personId = 999;
        when(mealService.getFoodHistory(personId)).thenThrow(new PersonNotFoundException(999));

        mvc.perform(get("/meal/history")
                        .param("personId", "999")
                        .param("date", String.valueOf(testDate)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Person not found with id: 999"));
    }

    @Test
    void checkDailyCalorieIntake_ShouldReturnCalorieResponse() throws Exception {
        MealCheckDailyCalorieResponse expectedResponse = new MealCheckDailyCalorieResponse(1900, 1700, 200, true, "Вы уложились в норму! Осталось 200 ккал");
        when(mealService.checkDailyCalorieIntake(personId, testDate)).thenReturn(expectedResponse);

        mvc.perform(get("/meal/daily-check")
                        .param("personId", String.valueOf(personId))
                        .param("date", testDate.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.difference").value(200))
                .andExpect(jsonPath("$.isWithinLimit").value(true));
        verify(mealService).checkDailyCalorieIntake(personId, testDate);
    }

    @Test
    void checkDailyCalorieIntake_WhenDateNotFound_ShouldThrowException() throws Exception {
        when(mealService.checkDailyCalorieIntake(personId, dateOld))
                .thenThrow(new DateNotFoundException("Date not found"));

        mvc.perform(get("/meal/daily-check")
                        .param("personId", "1")
                        .param("date", String.valueOf(dateOld)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Date not found"));

    }
}
