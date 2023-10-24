package project_test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class QuizAttempt {
    private int attemptId;
    private Student student;
    private String quiz;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Integer> selectedAnswers;

    public QuizAttempt(int attemptId, Student student,String  quiz) {
        this.attemptId = attemptId;
        this.student = student;
        this.quiz = quiz;
        this.startTime = LocalDateTime.now();
        this.endTime = null;
        this.selectedAnswers = new ArrayList<>();
    }

    public void submitAnswer(Question question, int answerIndex) {
        selectedAnswers.add(answerIndex);
    }

    public void submitQuiz() {
        this.endTime = LocalDateTime.now();
    }

    public int getScore() {
        int score = 0;
        

        return score;
    }

    public int getAttemptId() {
        return attemptId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Student getStudent() {
        return student;
    }

    public String getQuiz() {
        return quiz;
    }
    
    // Additional method to get QuizAttempt by id
    public static QuizAttempt getQuizAttemptById(List<QuizAttempt> quizAttempts, int attemptId) {
        for (QuizAttempt attempt : quizAttempts) {
            if (attempt.getAttemptId() == attemptId) {
                return attempt;
            }
        }
        return null; // If no QuizAttempt found with the given id
    }
}
