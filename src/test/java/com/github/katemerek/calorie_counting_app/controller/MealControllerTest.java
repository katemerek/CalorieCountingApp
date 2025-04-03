package com.github.katemerek.calorie_counting_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.katemerek.calorie_counting_app.dto.MealDailyDto;
import com.github.katemerek.calorie_counting_app.dto.MealToAddDto;
import com.github.katemerek.calorie_counting_app.mapper.MealMapper;
import com.github.katemerek.calorie_counting_app.model.Meal;
import com.github.katemerek.calorie_counting_app.repository.PeopleRepository;
import com.github.katemerek.calorie_counting_app.response.MealDailyResponse;
import com.github.katemerek.calorie_counting_app.service.MealService;
import com.github.katemerek.calorie_counting_app.service.PersonService;
import com.github.katemerek.calorie_counting_app.util.DateNotFoundException;
import com.github.katemerek.calorie_counting_app.util.PersonNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static com.github.katemerek.calorie_counting_app.enumiration.TypeOfMeal.BREAKFAST;
import static com.github.katemerek.calorie_counting_app.enumiration.TypeOfMeal.LUNCH;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MealController.class)
@ExtendWith(MockitoExtension.class)
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
        mealToAddDto.setType(BREAKFAST.name());
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

    @Test
    void getDailyMeals_shouldReturnDailyMealsForValidPersonAndDate() throws PersonNotFoundException, DateNotFoundException {

        int personId = 1;
        LocalDate date = LocalDate.of(2025, 3, 1);
        MealDailyResponse expectedResponse = new MealDailyResponse(date, personId, getMealDailyDtos(), 1800);

        when(mealService.getDailyMeals(personId, date)).thenReturn(expectedResponse);
        MealDailyResponse actualResponse = mealController.getDailyMeals(personId, date);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(mealService).getDailyMeals(personId, date);
    }

    @Test
    void getDailyMeals_shouldThrowPersonNotFoundException() throws PersonNotFoundException, DateNotFoundException {

        int personId = 1000;
        LocalDate date = LocalDate.now();

        when(mealService.getDailyMeals(personId, date)).thenThrow(new PersonNotFoundException(1000));

        assertThrows(PersonNotFoundException.class, () -> mealController.getDailyMeals(personId, date));
    }

    @Test
    void getDailyMeals_shouldThrowDateNotFoundException() throws PersonNotFoundException, DateNotFoundException {
        int personId = 1;
        LocalDate date = LocalDate.of(1900, 1, 1);

        when(mealService.getDailyMeals(personId, date)).thenThrow(new DateNotFoundException("Date not found"));
        assertThrows(DateNotFoundException.class, () -> mealController.getDailyMeals(personId, date));
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
