package tric.tricproject.Repository;

import org.springframework.data.jpa.repository.Query;
import tric.tricproject.Model.PlayResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PlayResultRepository extends JpaRepository<PlayResult, Long> {

    @Query
    List<PlayResult> findByDate(Timestamp date);
}
