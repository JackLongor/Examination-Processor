131376 Exam processor

# Java Examination Processing System

## Description

This is a simple console-based Java application designed to process examination results for a university department. It allows users to:

1.  Enter details (Name, ID Number) for multiple students.
2.  Record scores for a predefined set of subjects for each student.
3.  Calculate the average score based on the entered subject scores.
4.  Determine an overall grade (e.g., A, B, C, D, E) based on the average.
5.  Provide a simple recommendation (e.g., Excellent, Good, Poor).
6.  Display a formatted report card for each student on the console.

The application is designed to handle at least 10 students and at least 5 subjects per student, fulfilling common departmental requirements. It includes basic input validation for scores.

## Features

* **Student Data Entry:** Collects Name and ID for a minimum number of students, with an option to add more.
* **Subject Score Entry:** Prompts for scores (0-100) for each predefined subject. Includes numeric input validation.
* **Average Calculation:** Calculates the average score across all subjects for each student.
* **Grading:** Assigns a letter grade based on a defined scale (see **Grading System** below).
* **Recommendations:** Provides a brief performance recommendation based on the grade.
* **Report Card Generation:** Displays a formatted report card including:
    * Student Name and ID
    * List of Subjects and Scores
    * Average Score (formatted to 2 decimal places)
    * Overall Grade
    * Recommendation
    * Date the report is generated (using the system's current date).

## Technology Used

* **Java:** Requires JDK (Java Development Kit) version 8 or higher (due to the use of `java.time` package and Streams API).

## Setup & Prerequisites

* Ensure you have a Java Development Kit (JDK) version 8 or later installed on your system.
* Verify your installation by opening a terminal or command prompt and running:
    ```bash
    java -version
    javac -version
    ```

## How to Compile and Run

1.  **Save:** Save the provided Java code as `ExaminationProcessor.java`.
2.  **Compile:** Open your terminal or command prompt, navigate (`cd`) to the directory where you saved the file, and compile the code using the Java compiler:
    ```bash
    javac ExaminationProcessor.java
    ```
    This will create `ExaminationProcessor.class` and `Student.class` files.
3.  **Run:** Execute the compiled application using the Java Virtual Machine (JVM):
    ```bash
    java ExaminationProcessor
    ```
4.  **Interact:** Follow the on-screen prompts to enter the required student details and their scores for each subject. Once all data for all students is entered, the program will display the report cards.

## Code Structure

The application consists of a single source file (`ExaminationProcessor.java`) containing two classes:

* **`Student` (package-private class):** A simple data class (POJO) to hold information for a single student (name, ID, subjects, scores, average, grade, recommendation).
* **`ExaminationProcessor` (public class):** Contains the main application logic.
    * `main` method: The entry point of the program, orchestrates the workflow.
    * Static methods: Organised logically to handle specific tasks:
        * Collecting student details (`collectStudentDetails`, `createStudent`, `addAdditionalStudents`).
        * Collecting scores (`collectScoresForStudents`, `inputScoresForStudent`, `getValidScore`).
        * Calculating results (`calculateAverageAndGrade`, `setGradeAndRecommendation`).
        * Displaying reports (`displayReportCards`, `displayReportCard`).
    * Constants: Defines minimum students, subject names, and date format.

## Configuration

You can modify the following constants directly within the `ExaminationProcessor.java` file if needed:

* `MIN_STUDENTS`: Change the minimum number of students required (default is 10).
* `SUBJECT_NAMES`: Modify the array to change the names or number of subjects (ensure at least 5 are listed if changing).

## Grading System

The current grading scale is implemented within the `setGradeAndRecommendation` method. The default scale is:

* **A:** 70 - 100 (Excellent performance.)
* **B:** 60 - 69 (Good performance.)
* **C:** 50 - 59 (Average performance.)
* **D:** 40 - 49 (Poor performance. Improvement needed.)
* **E:** 0 - 39 (Fail. Significant improvement required.)

This scale can be easily adjusted by modifying the `if-else if` conditions within that method.
