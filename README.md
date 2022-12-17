# GPA Calculator for the University of Puerto Rico - Mayaguez

## How the GPA is calculated

Based on [this](https://www.uprm.edu/registrar/question_answer.php?id=26) FAQ answer,
the GPA is calculated by dividing the total points of honor by the number of credits.
Where the points of honor are based on the grade achieved on the class:

| Grade | Points of Honor |
|-------|-----------------|
| A     | 4               |
| B     | 3               |
| C     | 2               |
| D     | 1               |
| F     | 0               |

Classes with grade `W` are not included in the calculation of the GPA.

## Curriculum JSON format

The JSON file passed into the `Curriculum.fromJson` function must be formatted as a map with
two keys,  `credits` and `semesters`, and the value being a list of maps where every map represents one
semester. A semester has two keys, `name` and `courses`, where the value is a string and a
list respectively. The list of courses will contain maps, where each map will represent
one course. Each course will contain 4 keys, `code`, `name`, `credits` and `grade`, each
one has a string as a value, except credits, which will have an integer as value. The code
will end with an asterisk if it is a concentration class.

The resulting JSON file will look something like this:

    {
      "credits": 100,
      "semesters": [
        {
          "name": "First year, first semester",
          "courses": [
            {
              "code": "CIIC3015*",
              "name": "Introduction to Computer Programming I",
              "credits": 4,
              "grade": "A"
            },
            ...
          ]
        },
        ...
      ]
    }

## Dependency

This project depends on [this](https://github.com/diego-velez/Tabletery) project. You must run the
`downloadTabletery` Gradle task before building the app.