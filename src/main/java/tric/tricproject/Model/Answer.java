package tric.tricproject.Model;

import javax.persistence.*;

@Entity
@Table(name="answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long answerId;

    @Column(name = "text")
    private String answerText;
    @ManyToOne
    @JoinColumn(name="questionId", nullable=false)
    private Question question;

    @Column(name = "category")
    private String category;

    public Answer(int answerId, String answerText, Question question, String category) {
        this.answerId = answerId;
        this.answerText = answerText;
        this.question = question;
        this.category = category ;
    }
    public Answer(String answerText, Question question, String category) {
        this.answerText = answerText;
        this.question = question;
        this.category = category ;
    }

    public Answer(int answerId, String answerText) {
        this.answerId = answerId;
        this.answerText = answerText;
    }
    public Answer() {
    }

    public Answer(String answerText) {
        this.answerText = answerText;
    }

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
