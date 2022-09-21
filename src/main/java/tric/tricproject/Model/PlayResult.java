package tric.tricproject.Model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name="playResult")
public class PlayResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long playResultId;
    @Column(name = "date")
    private Timestamp date;
    @Column(name = "questionNumber")
    private int questionNumber;
    @Column(name = "firstAnswer")
    private double firstAnswerRate;
    @Column(name = "secondAnswer")
    private double secondAnswerRate;

    public PlayResult(long playResultId, Timestamp date, int questionNumber, double firstAnswerRate, double secondAnswerRate) {
        this.playResultId = playResultId;
        this.date = date;
        this.questionNumber = questionNumber;
        this.firstAnswerRate = firstAnswerRate;
        this.secondAnswerRate = secondAnswerRate;
    }

    public PlayResult(Timestamp date, int questionNumber, double firstAnswerRate, double secondAnswerRate) {
        this.date = date;
        this.questionNumber = questionNumber;
        this.firstAnswerRate = firstAnswerRate;
        this.secondAnswerRate = secondAnswerRate;
    }

    public PlayResult() {
    }

    public long getPlayResultId() {
        return playResultId;
    }

    public void setPlayResultId(long playResultId) {
        this.playResultId = playResultId;
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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getDateString(){
        return new SimpleDateFormat("MM/dd/yyyy").format(new Date(date.getTime()));
    }
}
