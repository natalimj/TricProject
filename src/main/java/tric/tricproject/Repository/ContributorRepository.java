package tric.tricproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tric.tricproject.Model.Contributor;
import java.util.List;

@Repository
public interface ContributorRepository extends JpaRepository<Contributor, Long> {
    @Query
    List<Contributor> findAllByType(String type);
}
