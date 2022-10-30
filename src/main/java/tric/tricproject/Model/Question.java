package tric.tricproject.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long questionId;

    @Column(name = "number")
    private int questionNumber;
    @Column(name = "text")
    private String questionText;

    @Column(name = "time")
    private int time;

    @Column(name = "theme")
    private String theme;

    @OneToMany(mappedBy="question",cascade={CascadeType.ALL})
    private List<Answer> answers=   new ArrayList<>();

    public Question(long questionId, int questionNumber, String questionText) {
        this.questionId = questionId;
        this.questionNumber = questionNumber;
        this.questionText = questionText;
    }
    public Question(int questionNumber, String questionText) {
        this.questionNumber = questionNumber;
        this.questionText = questionText;
    }

    public Question(long questionId, int questionNumber, String questionText, int time, String theme, List<Answer> answers) {
        this.questionId = questionId;
        this.questionNumber = questionNumber;
        this.questionText = questionText;
        this.time = time;
        this.theme = theme;
        this.answers = answers;
    }

    public Question() {

    }

    public Question(String questionText) {
        this.questionText = questionText;
    }
    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
