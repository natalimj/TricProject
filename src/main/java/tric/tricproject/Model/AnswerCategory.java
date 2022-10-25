package tric.tricproject.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name="answer_category")
public class AnswerCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long answerCategoryId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="answerId", nullable=false)
    private Answer answerId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="categoryId", nullable=false)
    private Category categoryId;

    public AnswerCategory(long answerCategoryId, Answer answerId, Category categoryId) {
        this.answerCategoryId = answerCategoryId;
        this.answerId = answerId;
        this.categoryId = categoryId;
    }

    public AnswerCategory(Answer answerId, Category categoryId) {
        this.answerId = answerId;
        this.categoryId = categoryId;
    }

    public AnswerCategory() {}

    public long getAnswerCategoryId() {
        return answerCategoryId;
    }

    public void setAnswerCategoryId(long answerCategoryId) {
        this.answerCategoryId = answerCategoryId;
    }

    public Answer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Answer answerId) {
        this.answerId = answerId;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }
}
