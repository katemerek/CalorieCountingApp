package com.github.katemerek.calorie_counting_app.controller;

import com.github.katemerek.calorie_counting_app.dto.IdResponse;
import com.github.katemerek.calorie_counting_app.dto.PersonDto;
import com.github.katemerek.calorie_counting_app.mapper.PersonMapper;
import com.github.katemerek.calorie_counting_app.model.Person;
import com.github.katemerek.calorie_counting_app.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;
    private final PersonMapper personMapper;

    @GetMapping
    public List<PersonDto> getAllPeople() {
        return personService.getAllPeople()
                .stream()
                .map(personMapper::toPersonDto)
                .toList();
    }

    @PostMapping("/registration")
    public ResponseEntity<IdResponse> registrationPerson(@RequestBody @Valid PersonDto personDto) {
        Person personToAdd = personMapper.toPerson(personDto);
        personService.save(personToAdd);
        IdResponse response = new IdResponse(
                personToAdd.getId(),
                "New person created successfully"
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
