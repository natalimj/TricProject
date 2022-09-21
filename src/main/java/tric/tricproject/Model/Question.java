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
}
