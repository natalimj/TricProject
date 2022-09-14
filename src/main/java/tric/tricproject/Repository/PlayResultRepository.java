package tric.tricproject.Repository;

import tric.tricproject.Model.PlayResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayResultRepository extends JpaRepository<PlayResult, Long> {
}
