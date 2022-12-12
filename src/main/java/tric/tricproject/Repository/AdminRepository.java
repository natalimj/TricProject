package tric.tricproject.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tric.tricproject.Model.Admin;

/**
 * Repository interface extends JpaRepository
 * to perform CRUD operations for {@link Admin}
 *
 * @author Bogdan Mezei
 * @version 1.0, October 2022
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
	Optional<Admin> findByUsername(String username);

}