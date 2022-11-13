package tric.tricproject.Repository;


import tric.tricproject.Model.Question;
import tric.tricproject.Model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query
    List<Vote> findAllByQuestionId(long questionId);

    List<Vote> findAllByUserId(long questionId);
}
