package tric.tricproject.Model;

public class Result {

    private int questionNumber;
    private Answer firstAnswer;
    private Answer secondAnswer;
    private double firstAnswerRate;
    private double secondAnswerRate;
    public Result() {
    }


    public Result(int questionNumber, double firstAnswerRate, double secondAnswerRate) {
        this.questionNumber = questionNumber;
        this.firstAnswerRate = firstAnswerRate;
        this.secondAnswerRate = secondAnswerRate;
    }

    public Result(int questionNumber, Answer firstAnswer, Answer secondAnswer, double firstAnswerRate, double secondAnswerRate) {
        this.questionNumber = questionNumber;
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

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }
}

