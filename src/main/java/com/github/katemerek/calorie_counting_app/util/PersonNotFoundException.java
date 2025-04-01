package com.github.katemerek.calorie_counting_app.util;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(int personId) {
        super("Person not found with id: " + personId);
    }
}
