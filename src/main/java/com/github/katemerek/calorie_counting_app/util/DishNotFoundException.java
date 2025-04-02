package com.github.katemerek.calorie_counting_app.util;

public class DishNotFoundException extends RuntimeException {
  public DishNotFoundException(int dishId) {
    super("Dish not found with id: " + dishId);
  }
}
