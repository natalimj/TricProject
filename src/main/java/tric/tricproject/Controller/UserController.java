package tric.tricproject.Controller;

import tric.tricproject.Model.User;
import tric.tricproject.Model.Vote;
import tric.tricproject.Service.UserService;
import tric.tricproject.Service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/userApi")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;
    @Autowired
    VoteService voteService;
    @Autowired
    SimpMessagingTemplate template;
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User _user = userService.addUser(user);
            template.convertAndSend("/topic/message",userService.getAllUsers().size());
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/vote")
    public ResponseEntity<Vote> addVote(@RequestBody Vote vote) {
        try {
            Vote _vote = voteService.addVote(vote);
            return new ResponseEntity<>(_vote, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
