import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// Represents a single student with their details and exam results
class Student {
    private String name;
    private String idNumber;
    private List<String> subjects;
    private List<Double> scores;
    private double averageScore;
    private String grade;
    private String recommendation;

    public Student(String name, String idNumber) {
        this.name = name;
        this.idNumber = idNumber;
        this.subjects = new ArrayList<>();
        this.scores = new ArrayList<>();
        this.averageScore = 0.0;
        this.grade = "N/A";
        this.recommendation = "N/A";
    }

    // --- Getters ---
    public String getName() { return name; }
    public String getIdNumber() { return idNumber; }
    public List<String> getSubjects() { return subjects; }
    public List<Double> getScores() { return scores; }
    public double getAverageScore() { return averageScore; }
    public String getGrade() { return grade; }
    public String getRecommendation() { return recommendation; }

    // --- Setters / Data Modifiers ---
    public void addSubjectScore(String subject, double score) {
        subjects.add(subject);
        scores.add(score);
    }
    public void setAverageScore(double averageScore) { this.averageScore = averageScore; }
    public void setGrade(String grade) { this.grade = grade; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }
}

// Main class containing the logic for processing student examinations
public class ExaminationProcessor {

    // --- Constants ---
    private static final int MIN_STUDENTS = 10; // Minimum number of students required
    private static final String[] SUBJECT_NAMES = { // Subjects included in the examination
            "Financial Accounting",
            "Business Law",
            "Principles of Management",
            "Microeconomics",
            "Business Statistics"
    };
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Format for dates on reports

    // --- Main Program Entry Point ---
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Examination Processing System");
        System.out.println("Current Date: " + LocalDate.now().format(DATE_FORMATTER) + " (Location: Nairobi, Kenya)"); // Show current date and location

        // 1. Collect student details
        List<Student> students = collectStudentDetails(scanner);

        // 2. Collect scores and calculate results for each student
        collectScoresForStudents(scanner, students);

        // 3. Display report cards
        displayReportCards(students);

        System.out.println("\nExamination processing complete.");
        scanner.close(); // Release scanner resources
    }

    // Task 1: Collects details for all students (minimum + optional additional)
    private static List<Student> collectStudentDetails(Scanner scanner) {
        List<Student> students = new ArrayList<>();
        System.out.println("--- Enter Student Details ---");
        System.out.println("Enter details for at least " + MIN_STUDENTS + " students.");

        // Get minimum required students
        while (students.size() < MIN_STUDENTS) {
            students.add(createStudent(scanner, students.size() + 1));
        }

        // Option to add more students
        addAdditionalStudents(scanner, students);

        System.out.println("\nEntered details for " + students.size() + " students.");
        return students;
    }

    // Helper to create a single student instance with validated input
    private static Student createStudent(Scanner scanner, int studentNumber) {
        String name = "";
        String idNumber = "";

        System.out.println("\nEnter details for Student #" + studentNumber);

        // Validate that name and ID are not empty
        while (name.isEmpty() || idNumber.isEmpty()) {
            System.out.print("Enter Student Name: ");
            name = scanner.nextLine().trim();

            System.out.print("Enter Student ID Number: ");
            idNumber = scanner.nextLine().trim();

            if (name.isEmpty() || idNumber.isEmpty()) {
                System.out.println("Error: Student name and ID cannot be empty.");
            }
        }
        return new Student(name, idNumber);
    }

    // Helper to prompt user for adding more students
    private static void addAdditionalStudents(Scanner scanner, List<Student> students) {
        while (true) {
            System.out.print("\nAdd another student? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("yes") || response.equals("y")) {
                students.add(createStudent(scanner, students.size() + 1));
            } else if (response.equals("no") || response.equals("n")) {
                break; // Exit loop if user enters 'no' or 'n'
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
    }

    // Task 2: Iterates through students to collect scores and calculate results
    private static void collectScoresForStudents(Scanner scanner, List<Student> students) {
        System.out.println("\n--- Proceeding to Score Entry ---");
        for (Student student : students) {
            inputScoresForStudent(scanner, student);
            calculateAverageAndGrade(student);
        }
    }

    // Gets scores for all defined subjects for one student
    private static void inputScoresForStudent(Scanner scanner, Student student) {
        System.out.println("\nEnter Scores for " + student.getName() + " (ID: " + student.getIdNumber() + ")");
        for (String subject : SUBJECT_NAMES) {
            double score = getValidScore(scanner, subject);
            student.addSubjectScore(subject, score);
        }
    }

    // Gets a single valid score (0-100) from the user for a subject
    private static double getValidScore(Scanner scanner, String subject) {
        double score = -1.0; // Initial invalid value
        while (score < 0 || score > 100) {
            System.out.print("Enter score for " + subject + " (0-100): ");
            try {
                score = scanner.nextDouble();
                if (score < 0 || score > 100) {
                    System.out.println("Error: Score must be between 0 and 100.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input to prevent infinite loop
                score = -1.0; // Reset score to continue loop
            }
        }
        scanner.nextLine(); // Consume the newline left-over after nextDouble()
        return score;
    }

    // Task 3: Calculates average score, grade, and recommendation
    private static void calculateAverageAndGrade(Student student) {
        List<Double> scores = student.getScores();
        if (scores.isEmpty()) { // Prevent division by zero if no scores
            student.setAverageScore(0.0);
            student.setGrade("N/A");
            student.setRecommendation("No scores entered");
            return;
        }

        double total = scores.stream().mapToDouble(Double::doubleValue).sum();
        double average = total / scores.size();
        student.setAverageScore(average);

        // Determine grade and recommendation based on average
        setGradeAndRecommendation(student, average);
    }

    // Sets the grade string and recommendation based on the average score
    private static void setGradeAndRecommendation(Student student, double average) {
        String grade;
        String recommendation;

        // Standard Grading Scale (Example)
        if (average >= 70) { grade = "A"; recommendation = "Excellent performance."; }
        else if (average >= 60) { grade = "B"; recommendation = "Good performance."; }
        else if (average >= 50) { grade = "C"; recommendation = "Average performance."; }
        else if (average >= 40) { grade = "D"; recommendation = "Poor performance. Improvement needed."; }
        else { grade = "E"; recommendation = "Fail. Significant improvement required."; }

        student.setGrade(grade);
        student.setRecommendation(recommendation);
    }

    // Task 4: Iterates through students to display their report cards
    private static void displayReportCards(List<Student> students) {
        System.out.println("\n--- Generating Report Cards ---");
        for (Student student : students) {
            displayReportCard(student);
        }
    }

    // Formats and prints the report card for a single student
    private static void displayReportCard(Student student) {
        LocalDate today = LocalDate.now(); // Get current date for the report

        System.out.println("\n========================================");
        System.out.println("        UNIVERSITY REPORT CARD          ");
        System.out.println("========================================");
        System.out.println("Date Generated: " + today.format(DATE_FORMATTER));
        System.out.println("----------------------------------------");
        System.out.println("Student Name: " + student.getName());
        System.out.println("Student ID:   " + student.getIdNumber());
        System.out.println("----------------------------------------");
        System.out.println("Subject Scores:");

        List<String> subjects = student.getSubjects();
        List<Double> scores = student.getScores();
        if (subjects.isEmpty()) {
            System.out.println("  No subjects/scores recorded.");
        } else {
            for (int i = 0; i < subjects.size(); i++) {
                // Formatted printing for alignment
                System.out.printf("  %-30s: %.1f\n", subjects.get(i), scores.get(i));
            }
        }

        System.out.println("----------------------------------------");
        System.out.printf("Average Score: %.2f\n", student.getAverageScore());
        System.out.println("Overall Grade: " + student.getGrade());
        System.out.println("Recommendation: " + student.getRecommendation());
        System.out.println("========================================\n");
    }
}