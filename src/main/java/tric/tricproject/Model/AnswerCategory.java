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

    @JoinColumn(name="answerId", nullable=false)
    private long answerId;

    @JoinColumn(name="categoryId", nullable=false)
    private long categoryId;

    public AnswerCategory(long answerCategoryId, long answerId, long categoryId) {
        this.answerCategoryId = answerCategoryId;
        this.answerId = answerId;
        this.categoryId = categoryId;
    }

    public AnswerCategory(long answerId, long categoryId) {
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

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
