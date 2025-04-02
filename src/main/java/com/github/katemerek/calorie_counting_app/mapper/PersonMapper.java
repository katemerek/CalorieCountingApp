package com.github.katemerek.calorie_counting_app.mapper;

import com.github.katemerek.calorie_counting_app.dto.PersonDto;
import com.github.katemerek.calorie_counting_app.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper {
    Person toPerson(PersonDto personDto);

    PersonDto toPersonDto(Person person);
}
