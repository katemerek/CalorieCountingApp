package com.github.katemerek.calorie_counting_app.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IdResponse {

    private int id;

    private String message;
}
