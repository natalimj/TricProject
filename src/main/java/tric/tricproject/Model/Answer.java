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

    @OneToOne()
    private AnswerCategory answerCategory;

    public Answer(int answerId, String answerText, Question question) {
        this.answerId = answerId;
        this.answerText = answerText;
        this.question = question;
    }
    public Answer(String answerText, Question question) {
        this.answerText = answerText;
        this.question = question;
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


}
