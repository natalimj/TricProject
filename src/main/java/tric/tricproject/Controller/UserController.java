package tric.tricproject.Controller;

import tric.tricproject.Model.*;
import tric.tricproject.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/userApi")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    VoteService voteService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    StatusService statusService;
    @Autowired
    SimpMessagingTemplate template;
    @Autowired
    QuestionService questionService;

    @Autowired
    PlayInfoService playInfoService;

    @Autowired
    ContributorService contributorService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
    @GetMapping("/getAppStatus")
    public ResponseEntity<Boolean> getAppStatus() {
        try {
            Boolean isActive = statusService.getStatus();
            return new ResponseEntity<>(isActive, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/result")  //TODO: maybe it can be only a service method - call with timer?
    public ResponseEntity<Result> getResult(@RequestParam("questionId") long questionId) {
        try {
            Result result = questionService.getResult(questionId);
            template.convertAndSend("/topic/result", result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/finalResult")
    public ResponseEntity<List<FinalResult>> getFinalResult(@RequestParam("userId") long userId) {
        try {
            List<Category> categories = categoryService.getAllCategories();
            List<FinalResult> finalResults = categoryService.getFinalResults(userId, categories);
            return new ResponseEntity<>(finalResults , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cast")
    public ResponseEntity<List<Contributor>> getCast() {
        try {
            List<Contributor> contributors = contributorService.getCast();
            return new ResponseEntity<>(contributors, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/devTeam")
    public ResponseEntity<List<Contributor>> getDevTeam() {
        try {
            List<Contributor> contributors = contributorService.getDevTeam();
            return new ResponseEntity<>(contributors, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getPlayInfo")
    public ResponseEntity<PlayInfo> getPlayInfo() {
        try {
            PlayInfo playInfo = playInfoService.getPlayInfo();
            return new ResponseEntity<>(playInfo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
