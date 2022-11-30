package tric.tricproject.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tric.tricproject.Model.ERole;
import tric.tricproject.Model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
