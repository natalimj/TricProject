package tric.tricproject.Service;

import tric.tricproject.Model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public User addUser(User user);
    public List<User> getAllUsers();
    public void deleteAllUsers();
    // public Vote getUserVote(int userId, int questionNumber) ;


}
