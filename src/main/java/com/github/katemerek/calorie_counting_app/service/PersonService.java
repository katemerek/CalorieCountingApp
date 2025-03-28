package com.github.katemerek.calorie_counting_app.service;

import com.github.katemerek.calorie_counting_app.model.Person;
import com.github.katemerek.calorie_counting_app.repository.PeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonService {
    private final PeopleRepository peopleRepository;

    @Transactional
    public Person save(Person person) {
        enrichPerson(measurement);
        return peopleRepository.save(person);
    }

    public void supplementPerson(Person person) {

        person.setDailyCalorieIntake();
        measurement.setSensor(sensorsService.findByName(measurement.getSensor().getName()).get());
        measurement.setTimeMeasurement(LocalDateTime.now());
    }
}
