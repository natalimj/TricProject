package tric.tricproject.Repository;

import tric.tricproject.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface extends JpaRepository
 * to perform CRUD operations for {@link User}
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0, October 2022
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>  {
}
