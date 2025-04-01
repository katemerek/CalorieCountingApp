package com.github.katemerek.calorie_counting_app.util;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super(message);
    }
}
