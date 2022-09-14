package tric.tricproject.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="playResult")
public class PlayResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "date")
    private Date date;
    @Column(name = "questionNumber")
    private int questionNumber;
    @Column(name = "firstAnswer")
    private double firstAnswer;
    @Column(name = "secondAnswer")
    private double secondAnswer;

    public PlayResult(long id, Date date, int questionNumber, double firstAnswer, double secondAnswer) {
        this.id = id;
        this.date = date;
        this.questionNumber = questionNumber;
        this.firstAnswer = firstAnswer;
        this.secondAnswer = secondAnswer;
    }

    public PlayResult() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public double getFirstAnswer() {
        return firstAnswer;
    }

    public void setFirstAnswer(double firstAnswer) {
        this.firstAnswer = firstAnswer;
    }

    public double getSecondAnswer() {
        return secondAnswer;
    }

    public void setSecondAnswer(double secondAnswer) {
        this.secondAnswer = secondAnswer;
    }
}
