package com.github.katemerek.calorie_counting_app.response;

import com.github.katemerek.calorie_counting_app.dto.MealHistoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class MealHistoryResponse {

        private int personId;

        private List<MealHistoryDto> mealsHistoryDto;
    }
