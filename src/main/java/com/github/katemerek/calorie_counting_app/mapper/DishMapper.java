package com.github.katemerek.calorie_counting_app.mapper;

import com.github.katemerek.calorie_counting_app.dto.DishDto;
import com.github.katemerek.calorie_counting_app.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DishMapper {
    Dish toDish(DishDto dishDto);

    DishDto toDishDto(Dish dish);
}
