package tric.tricproject.Repository;

import tric.tricproject.Model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface extends JpaRepository
 * to perform CRUD operations for {@link Vote}
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0, October 2022
 */
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    @Query
    List<Vote> findAllByQuestionId(long questionId);

    @Query
    List<Vote> findAllByUserId(long questionId);
}
