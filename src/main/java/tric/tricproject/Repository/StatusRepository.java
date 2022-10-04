package tric.tricproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tric.tricproject.Model.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
}
