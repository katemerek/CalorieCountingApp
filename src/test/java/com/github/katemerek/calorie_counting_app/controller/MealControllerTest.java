package com.github.katemerek.calorie_counting_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.katemerek.calorie_counting_app.dto.MealToAddDto;
import com.github.katemerek.calorie_counting_app.mapper.MealMapper;
import com.github.katemerek.calorie_counting_app.model.Meal;
import com.github.katemerek.calorie_counting_app.service.MealService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static com.github.katemerek.calorie_counting_app.enumiration.TypeOfMeal.BREAKFAST;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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

    @InjectMocks
    private MealController mealController;

    private List<Meal> getMeals() {
        Meal one = new Meal();
        Meal two = new Meal();
        return List.of(one, two);
    }

    @Test
    void getAllMeals_ShouldReturnListOfMealDtos() throws Exception {
        when(mealService.getAllMeal()).thenReturn(getMeals());
        mvc.perform(get("/meal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void addMeal_ShouldReturnCreatedStatus() throws Exception {

        MealToAddDto mealToAddDto = new MealToAddDto();
        mealToAddDto.setDate(LocalDate.of (2025, 3, 1));
        mealToAddDto.setPersonId(1);
        mealToAddDto.setType("BREAKFAST");
        mealToAddDto.setDishId(5);
        mealToAddDto.setDishWeight(300);
        mealToAddDto.setDishCalories(450);

        Meal meal = new Meal();
        meal.setMeal_id(3);
        meal.setDate(LocalDate.of (2025, 3, 1));
        meal.setType(BREAKFAST);
        meal.setDishWeight(300);
        meal.setDishCalories(450);

        when(mealMapper.toMeal(any(MealToAddDto.class))).thenReturn(meal);
        when(mealService.save(any(Meal.class))).thenReturn(meal.getMeal_id());

        mvc.perform(post("/meal/add")
                        .content(mapper.writeValueAsString(mealToAddDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3));
    }

    @Test
    void addMeal_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        MealToAddDto invalidDto = new MealToAddDto();

        mvc.perform(post("/meal/add")
                        .content(mapper.writeValueAsString(invalidDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

//    @Test
//    void getDailyMeals_ShouldReturnListOfMealDailyDtos(int personId, LocalDate date) throws Exception {
//        MealDailyResponse dailyResponse = new MealDailyResponse();
//        when(mealService.getDailyMeals(personId, date)).thenReturn(getMeals());
//        mvc.perform(get("/meal/daily"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2));
//    }
}
