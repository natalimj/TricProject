package tric.tricproject.Model;


/**
 * PlayResult class holds question, answers and their rates
 * This class is created to hold the basic information about the final result
 * to be saved in a JSON file.
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0, November 2022
 */
public class PlayResult {
    private int questionNumber;
    private String questionText;
    private String firstAnswer;
    private double firstAnswerRate;
    private String secondAnswer;
    private double secondAnswerRate;

    public PlayResult(int questionNumber, String questionText, String firstAnswer, double firstAnswerRate, String secondAnswer, double secondAnswerRate) {
        this.questionNumber = questionNumber;
        this.questionText = questionText;
        this.firstAnswer = firstAnswer;
        this.firstAnswerRate = firstAnswerRate;
        this.secondAnswer = secondAnswer;
        this.secondAnswerRate = secondAnswerRate;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getFirstAnswer() {
        return firstAnswer;
    }

    public void setFirstAnswer(String firstAnswer) {
        this.firstAnswer = firstAnswer;
    }

    public double getFirstAnswerRate() {
        return firstAnswerRate;
    }

    public void setFirstAnswerRate(double firstAnswerRate) {
        this.firstAnswerRate = firstAnswerRate;
    }

    public String getSecondAnswer() {
        return secondAnswer;
    }

    public void setSecondAnswer(String secondAnswer) {
        this.secondAnswer = secondAnswer;
    }

    public double getSecondAnswerRate() {
        return secondAnswerRate;
    }

    public void setSecondAnswerRate(double secondAnswerRate) {
        this.secondAnswerRate = secondAnswerRate;
    }
}
