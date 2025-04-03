package com.github.katemerek.calorie_counting_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.katemerek.calorie_counting_app.dto.DishDto;
import com.github.katemerek.calorie_counting_app.mapper.DishMapper;
import com.github.katemerek.calorie_counting_app.model.Dish;
import com.github.katemerek.calorie_counting_app.service.DishService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DishController.class)
public class DishControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private DishService dishService;

    @Autowired
    ObjectMapper mapper;

    @MockitoBean
    private DishMapper dishMapper;

    private List<Dish> getDishes() {
        Dish one = new Dish();
        Dish two = new Dish();
        return List.of(one, two);
    }

    private DishDto createDishDto() {
        DishDto dishDto = new DishDto();
        dishDto.setDish_name("Шарлотка");
        dishDto.setCalories(350);
        dishDto.setProteins(25);
        dishDto.setFats(16);
        dishDto.setCarbohydrates(70);
        return dishDto;
    }

    private Dish createDish() {
        Dish dish = new Dish();
        dish.setId(5);
        dish.setDish_name("Шарлотка");
        dish.setCalories(350);
        dish.setProteins(25);
        dish.setFats(16);
        dish.setCarbohydrates(70);
        return dish;
    }

    @Test
    void getAllDish_ShouldReturnListOfDishesDtos() throws Exception {
        when(dishService.getAllDishes()).thenReturn(getDishes());

        mvc.perform(get("/dish"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void addDish_ShouldReturnCreatedStatus() throws Exception {
        when(dishMapper.toDish(any(DishDto.class))).thenReturn(createDish());
        when(dishService.save(any(Dish.class))).thenReturn(createDish().getId());

        mvc.perform(post("/dish/add")
                        .content(mapper.writeValueAsString(createDishDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    void addDish_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        DishDto invalidDto = new DishDto();

        mvc.perform(post("/dish/add")
                        .content(mapper.writeValueAsString(invalidDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
