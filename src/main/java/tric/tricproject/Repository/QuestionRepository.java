package tric.tricproject.Repository;

import tric.tricproject.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface extends JpaRepository
 * to perform CRUD operations for {@link Question}
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0, October 2022
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query
    List<Question> findAllByOrderByQuestionIdAsc();
    @Query
    Question findByQuestionId(long questionId);
    @Query
    Question findByQuestionNumber(int questionNumber);

}