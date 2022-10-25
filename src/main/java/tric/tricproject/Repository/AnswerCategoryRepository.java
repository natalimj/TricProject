package tric.tricproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tric.tricproject.Model.AnswerCategory;

import java.util.List;

@Repository
public interface AnswerCategoryRepository extends JpaRepository<AnswerCategory, Long> {
    @Query
    List<AnswerCategory> findAllByAnswerId(long answerId);
}
