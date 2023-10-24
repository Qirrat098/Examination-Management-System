/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quiz {
    private String quizName;
    private Course course;
    private LocalDateTime dateTime;
    private int durationMinutes;
    private List<Question> questions;
    private Map<Student, List<Integer>> attempts;

    public Quiz(String quizName, Course course, LocalDateTime dateTime, int durationMinutes) {
        this.quizName = quizName;
        this.course = course;
        this.dateTime = dateTime;
        this.durationMinutes = durationMinutes;
        this.questions = new ArrayList<>();
        this.attempts = new HashMap<>();
    }

    public String getQuizName() {
        return quizName;
    }

    public Course getCourse() {
        return course;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Map<Student, List<Integer>> getAttempts() {
        return attempts;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void startAttempt(Student student) {
        if (!attempts.containsKey(student)) {
            attempts.put(student, new ArrayList<>());
            System.out.println("Quiz attempt started for student: " + student.getUsername());
        } else {
            System.out.println("Student has already started the quiz attempt.");
        }
    }

    public void submitAttempt(Student student, List<Integer> selectedAnswers) {
        if (attempts.containsKey(student)) {
            attempts.put(student, selectedAnswers);
            System.out.println("Quiz attempt submitted for student: " + student.getUsername());
        } else {
            System.out.println("Student has not started the quiz attempt.");
        }
    }
}
