package tric.tricproject.Service;

import tric.tricproject.Model.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface
 * containing methods to perform CRUD operations for {@link User}
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0, October 2022
 */
@Service
public interface UserService {
    User addUser(User user);
    List<User> getAllUsers();
    void deleteAllUsers();
}
