package tric.tricproject.Repository;

import tric.tricproject.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query
    List<Question> findAllByOrderByQuestionIdAsc();
    @Query
    Question findByQuestionId(long questionId);
    @Query
    Question findByQuestionNumber(int questionNumber);

    /*@Query
    Question findTopByOrderByQuestionIdAsc();

    @Query(value = "SELECT COUNT(v) FROM Vote v WHERE v.answer_id = :?",nativeQuery = true)
    int getNumberOfAnswer(long answerId);

    @Query(value = "SELECT COUNT(v) FROM Vote v WHERE v.question_id = :?",nativeQuery = true)
    int getNumberOfAnswers(long questionId);*/
}