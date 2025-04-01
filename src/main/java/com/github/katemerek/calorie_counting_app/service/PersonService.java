package com.github.katemerek.calorie_counting_app.service;

import com.github.katemerek.calorie_counting_app.enumiration.Gender;
import com.github.katemerek.calorie_counting_app.model.Person;
import com.github.katemerek.calorie_counting_app.repository.PeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonService {

    private final PeopleRepository peopleRepository;

    public List<Person> getAllPeople() {
        return peopleRepository.findAll();
    }

    @Transactional
    public void save(Person person) {
        supplementPerson(person);
        peopleRepository.save(person);
    }

    public void supplementPerson(Person person) {

        person.setDailyCalorieIntake(calculateDailyCalorieIntake(person));
    }

    /**
     * Формула Харриса-Бенедикта для расчета дневной нормы калорий:
     * Формула для мужчин:
     * BMR = 88,36 + (13,4 × вес в кг) + (4,8 × рост в см) – (5,7 × возраст в годах).
     * Формула для женщин:
     * BMR = 447,6 + (9,2 × вес в кг) + (3,1 × рост в см) – (4,3 × возраст в годах).
     **/
    public int calculateDailyCalorieIntake(Person person) {
        int dailyCalorieIntake;
        if (person.getGender().equals(Gender.MALE.name())) {
            dailyCalorieIntake = (int) (88.36 + (13.4 * person.getWeight()) + (4.8 * person.getHeight()) - (5.7 * person.getAge()));
        } else {
            dailyCalorieIntake = (int) (447.6 + (9.2 * person.getWeight()) + (3.1 * person.getHeight()) - (4.3 * person.getAge()));
        }
        return dailyCalorieIntake;
    }
}
