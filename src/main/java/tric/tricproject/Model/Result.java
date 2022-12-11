package tric.tricproject.Model;


/**
 * Result class is created as a DTO
 * to transfer voting result for a question
 * It holds the question, answers and answer rates
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0, November 2022
 */
public class Result {

    private Question question;
    private Answer firstAnswer;
    private Answer secondAnswer;
    private double firstAnswerRate;
    private double secondAnswerRate;
    public Result() {
    }


    public Result(Question question, double firstAnswerRate, double secondAnswerRate) {
        this.question = question;
        this.firstAnswerRate = firstAnswerRate;
        this.secondAnswerRate = secondAnswerRate;
    }

    public Result(Question question, Answer firstAnswer, Answer secondAnswer, double firstAnswerRate, double secondAnswerRate) {
        this.question = question;
        this.firstAnswer = firstAnswer;
        this.secondAnswer = secondAnswer;
        this.firstAnswerRate = firstAnswerRate;
        this.secondAnswerRate = secondAnswerRate;
    }

    public Answer getFirstAnswer() {
        return firstAnswer;
    }

    public void setFirstAnswer(Answer firstAnswer) {
        this.firstAnswer = firstAnswer;
    }

    public Answer getSecondAnswer() {
        return secondAnswer;
    }

    public void setSecondAnswer(Answer secondAnswer) {
        this.secondAnswer = secondAnswer;
    }

    public double getFirstAnswerRate() {
        return firstAnswerRate;
    }

    public void setFirstAnswerRate(double firstAnswerRate) {
        this.firstAnswerRate = firstAnswerRate;
    }

    public double getSecondAnswerRate() {
        return secondAnswerRate;
    }

    public void setSecondAnswerRate(double secondAnswerRate) {
        this.secondAnswerRate = secondAnswerRate;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}

