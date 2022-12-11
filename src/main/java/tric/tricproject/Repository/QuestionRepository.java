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

}