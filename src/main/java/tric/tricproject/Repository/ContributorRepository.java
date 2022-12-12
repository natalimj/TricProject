package tric.tricproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tric.tricproject.Model.Contributor;
import java.util.List;

/**
 * Repository interface extends JpaRepository
 * to perform CRUD operations for {@link Contributor}
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0, October 2022
 */
@Repository
public interface ContributorRepository extends JpaRepository<Contributor, Long> {
    @Query
    List<Contributor> findAllByType(String type);
}
