package com.github.katemerek.calorie_counting_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.katemerek.calorie_counting_app.dto.PersonDto;
import com.github.katemerek.calorie_counting_app.mapper.PersonMapper;
import com.github.katemerek.calorie_counting_app.model.Person;
import com.github.katemerek.calorie_counting_app.service.PersonService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.github.katemerek.calorie_counting_app.enumiration.Gender.MALE;
import static com.github.katemerek.calorie_counting_app.enumiration.Goal.WEIGHT_LOSS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PersonController.class)
@Import(PersonController.class)
public class PersonControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private PersonService personService;

    @Autowired
    ObjectMapper mapper;

    @MockitoBean
    private PersonMapper personMapper;

    @InjectMocks
    private PersonController personController;

    private List<Person> getPersons() {
        Person one = new Person();
        Person two = new Person();
        return List.of(one, two);
    }

    @Test
    void getAllPeople_ShouldReturnListOfPersonDtos() throws Exception {
        when(personService.getAllPeople()).thenReturn(getPersons());
        mvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void registrationPerson_ShouldReturnCreatedStatus() throws Exception {

        PersonDto personDto = new PersonDto();
        personDto.setUsername("Andrey");
        personDto.setGender("MALE");
        personDto.setAge(25);
        personDto.setEmail("and@mail.ru");
        personDto.setWeight(100);
        personDto.setHeight(160);
        personDto.setGoal("WEIGHT_LOSS");

        Person person = new Person();
        person.setId(1);
        person.setUsername("Andrey");
        person.setGender(MALE);
        person.setAge(25);
        person.setEmail("and@mail.ru");
        person.setWeight(100);
        person.setHeight(160);
        person.setGoal(WEIGHT_LOSS);

        when(personMapper.toPerson(any(PersonDto.class))).thenReturn(person);
        when(personService.save(any(Person.class))).thenReturn(person.getId());

        // 3. Выполнение запроса и проверки
        mvc.perform(post("/person/registration")  // Исправлен URL на правильный
                        .content(mapper.writeValueAsString(personDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())  // Ожидаем 201 Created вместо 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void registrationPerson_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        PersonDto invalidDto = new PersonDto(); // без обязательных полей

        mvc.perform(post("/person/registration")
                        .content(mapper.writeValueAsString(invalidDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
