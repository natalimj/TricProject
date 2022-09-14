package tric.tricproject.Model;

import javax.persistence.*;

@Entity
@Table(name="vote")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long voteId;

    @Column(name = "userId")
    private long userId;

    @Column(name = "questionId")
    private long questionId;


    @Column(name = "answerId")
    private long answerId;

    public Vote() {
    }

    public Vote(long voteId, long userId, long questionId, long answerId) {
        this.voteId = voteId;
        this.userId = userId;
        this.questionId = questionId;
        this.answerId = answerId;
    }

    public Vote( long userId, long questionId, long answerId) {
        this.userId = userId;
        this.questionId = questionId;
        this.answerId = answerId;
    }

    public long getVoteId() {
        return voteId;
    }

    public void setVoteId(long voteId) {
        this.voteId = voteId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }
}
