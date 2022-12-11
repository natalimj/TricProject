package tric.tricproject.Model;

import javax.persistence.*;

/**
 * Answer class holds information about an answer
 * including a unique answerId
 * The class is annotated with @Entity and @Table annotations
 * in order to map the object with a database table using Spring Data JPA
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0, September 2022
 */
@Entity
@Table(name="answer", schema ="voting")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long answerId;

    @Column(name = "text", length = 50)
    private String answerText;
    @ManyToOne
    @JoinColumn(name="questionId", nullable=false)
    private Question question;

    @Column(name = "firstCategory", length = 20)
    private String firstCategory;

    @Column(name = "secondCategory", length = 20)
    private String secondCategory;


    public Answer(int answerId, String answerText, Question question, String firstCategory, String secondCategory) {
        this.answerId = answerId;
        this.answerText = answerText;
        this.question = question;
        this.firstCategory = firstCategory ;
        this.secondCategory = secondCategory;
    }

    public Answer( String answerText,  String firstCategory, String secondCategory) {
        this.answerText = answerText;
        this.firstCategory = firstCategory ;
        this.secondCategory = secondCategory;
    }

    public Answer(String answerText, Question question, String firstCategory, String secondCategory) {
        this.answerText = answerText;
        this.question = question;
        this.firstCategory = firstCategory ;
        this.secondCategory = secondCategory;
    }

    public Answer(int answerId, String answerText, String firstCategory, String secondCategory) {
        this.answerId = answerId;
        this.answerText = answerText;
        this.firstCategory = firstCategory ;
        this.secondCategory = secondCategory;
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

    public String getFirstCategory() {
        return firstCategory;
    }

    public void setFirstCategory(String firstCategory) {
        this.firstCategory = firstCategory;
    }

    public String getSecondCategory() {
        return secondCategory;
    }

    public void setSecondCategory(String secondCategory) {
        this.secondCategory = secondCategory;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
