//package com.github.katemerek.calorie_counting_app.controller;
//
//import com.github.katemerek.calorie_counting_app.dto.ErrorResponse;
//import com.github.katemerek.calorie_counting_app.util.InvalidDataException;
//import com.github.katemerek.calorie_counting_app.util.PersonNotFoundException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestControllerAdvice
//public class ExceptionController {
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        List<String> errors = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(error -> error.getField() + ": " + error.getDefaultMessage())
//                .toList();
//
//        com.github.katemerek.calorie_counting_app.dto.ErrorResponse response = new ErrorResponse(
//                LocalDateTime.now(),
//                HttpStatus.BAD_REQUEST.value(),
//                "Validation failed",
//                errors.toString());
//
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler({PersonNotFoundException.class})
//    public ResponseEntity<ErrorResponse> handlePersonNotFoundExceptions(RuntimeException ex) {
//        ErrorResponse response = new ErrorResponse(
//                LocalDateTime.now(),
//                HttpStatus.NOT_FOUND.value(),
//                "Person not found",
//                ex.getMessage());
//
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(InvalidDataException.class)
//    public ResponseEntity<ErrorResponse> handleInvalidMealData(InvalidDataException ex) {
//        ErrorResponse response = new ErrorResponse(
//                LocalDateTime.now(),
//                HttpStatus.BAD_REQUEST.value(),
//                "Invalid data",
//                ex.getMessage());
//
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//}
