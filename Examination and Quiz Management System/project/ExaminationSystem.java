package project_test;

import java.util.ArrayList;
import java.util.List;

public class ExaminationSystem {
    private List<Instructor> instructors;
    private List<Student> students;
    private List<Course> courses;
    private List<Question> questionBank;
    private List<Quiz> quizzes;
    private List<QuizAttempt> quizAttempts; // New list to store quiz attempts

    public ExaminationSystem() {
        this.instructors = new ArrayList<>();
        this.students = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.questionBank = new ArrayList<>();
        this.quizzes = new ArrayList<>();
        this.quizAttempts = new ArrayList<>(); // Initialize the list of quiz attempts
    }
    public void createInstructor(String username, String password, String name) {
        Instructor instructor = new Instructor(username, password, name);
        instructors.add(instructor);
    }

    public void createStudent(String username, String password, String name) {
        Student student = new Student(username, password, name);
        students.add(student);
    }

    public void createCourse(String courseName, String courseCode, Instructor instructor) {
        Course course = new Course(courseName, courseCode, instructor);
        courses.add(course);
        instructor.addCourse(course);
    }

    public void addQuestion(Question question) {
        questionBank.add(question);
    }

    public void createQuiz(Quiz quiz) {
        quizzes.add(quiz);
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Question> getQuestionBank() {
        return questionBank;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void startQuiz(Student student, Quiz quiz) {
        quiz.startAttempt(student);
    }

    public void submitQuiz(Student student, Quiz quiz, List<Integer> selectedAnswers) {
        quiz.submitAttempt(student, selectedAnswers);
    }

        // ... Existing methods ...

    public List<QuizAttempt> getQuizAttempts() {
        return quizAttempts;
    }

    public QuizAttempt startQuizAttempt(Student student, Course course, String quiz) {
        // Check if the student, course, and quiz are valid
        if (students.contains(student) && courses.contains(course)) {
            // Check if the student is enrolled in the course
            if (student.getCourses().contains(course)) {
                QuizAttempt attempt = new QuizAttempt(generateNewAttemptId(), student, quiz);
                quizAttempts.add(attempt);
                return attempt;
            }
        }
        return null;
    }

    // Additional method to generate a new unique attempt ID
    private int generateNewAttemptId() {
        // Implementation to generate a unique attempt ID
        // You can use a counter or a random number generator, for example
        // For simplicity, let's assume that the ID is simply the next available integer
        int maxAttemptId = 0;
        for (QuizAttempt attempt : quizAttempts) {
            maxAttemptId = Math.max(maxAttemptId, attempt.getAttemptId());
        }
        return maxAttemptId + 1;
    }

    public QuizAttempt getQuizAttemptById(int attemptId) {
        for (QuizAttempt attempt : quizAttempts) {
            if (attempt.getAttemptId() == attemptId) {
                return attempt;
            }
        }
        return null; // If no QuizAttempt found with the given id
    }

 
}

