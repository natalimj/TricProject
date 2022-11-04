package tric.tricproject.Repository;


import org.springframework.data.jpa.repository.Query;
import tric.tricproject.Model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tric.tricproject.Model.Question;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query
    Answer findByAnswerId(long answerId);
}
