
package project_test;



import java.util.List;

public class Question {
    private String questionText;
    private String filename;
    private List<String> options;
    private int correctAnswerIndex;

    public Question(String questionText,String filename, List<String> options, int correctAnswerIndex) {
        this.questionText = questionText;
        this.filename = filename;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestionText() {
        return questionText;
    }
    
    public String getFileName() {
        return filename;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}
