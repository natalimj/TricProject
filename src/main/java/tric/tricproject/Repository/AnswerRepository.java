package tric.tricproject.Repository;


import org.springframework.data.jpa.repository.Query;
import tric.tricproject.Model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface extends JpaRepository
 * to perform CRUD operations for {@link Answer}
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0, October 2022
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query
    Answer findByAnswerId(long answerId);
}
