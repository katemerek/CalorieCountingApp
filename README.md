# CalorieCounterApp
## Description
Rest service for recording and storing user meals, checking whether the user has met the daily calorie intake.
This project is made as a test task using Spring Boot, Spring Data JPA.

### Steps to run the application
- Import this project as a Maven project.
- Create a PostgresSQL database:
```
  create database testprojectfor1221system
```
- Run the [person.sql, ish.sql and meal.sql]  (src/main/resources) in the database to create person, dish and meal tables with values.
- Add a new person to table person from POST request. To enter the gender use MALE or FEMALE, to enter the goal use: WEIGHT_LOSS, WEIGHT_MAINTENANCE or WEIGHT_GAIN.
```
{
    "username": "Екатерина",
    "gender": "FEMALE",
    "email": "pupu@kuku.ru",
    "age": 28,
    "weight": 70,
    "height": 180,
    "goal": "WEIGHT_MAINTENANCE"
}
```
- Add a new dishes to table dish from POST request.
```
{
    "dish_name": "Чизбургер",
    "calories": 302,
    "proteins": 13.5 ,
    "fats": 31.7,
    "carbohydrates": 16
}
```
- Add a new meals to table meal from POST request. To enter the type use BREAKFAST, LUNCH, DINNER or ANOTHER. Please check that you are entering an existing person and dish, otherwise you will not be able to add a record to the table.
  The date is entered in the ISO format yyyy-MM-dd.
```
{
    "personId": 3,
    "date": "2025-03-03",
    "type": "LUNCH",
    "dishId": 11,
    "dishWeight": 220.5
}
```

Test the API with Postman to perform various operations described below.
The app will start running at <http://localhost:8080>.

### REST APIs
CalorieCountingApp
This collection of requests is intended to interact with the CalorieCounterApp API.

#### Person
- GetAllPerson
http://localhost:8080/person
This request displays all persons in table person.
- AddPerson
http://localhost:8080/person/registration
This request adds a new person to the table.

#### Dish
- GetAllDishes
http://localhost:8080/dish
This request displays all dishes in table dish.

- AddDish
http://localhost:8080/dish/add
This request adds a new dish in table dish.

#### Meal
- GetAllMeals
http://localhost:8080/meal
This request displays all meals in table meal.

- AddMeal
http://localhost:8080/meal/add
This request adds a new meal in table meal.

- GetDailyMealsForPerson
http://localhost:8080/meal/daily?personId=2&date=2025-03-01
This request displays a daily report with the sum of all calories and meals.
Query Params: personId, date(ISO format yyyy-MM-dd).

- GetHistoryFoodsForPerson
http://localhost:8080/meal/history?personId=5
This query displays the food history by day.
Query Params: personId.

- CheckDailyCalorieIntake
http://localhost:8080/meal/daily-check?personId=2&date=2025-03-01
This query checks whether the user has met their daily calorie intake.
Query Params: personId, date(ISO format yyyy-MM-dd).