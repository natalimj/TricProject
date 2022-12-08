package tric.tricproject.Controller;

import tric.tricproject.Model.*;
import tric.tricproject.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling requests from the User
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0, September 2022
 */
@CrossOrigin
@RestController
@RequestMapping("/userApi")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    VoteService voteService;

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    QuestionService questionService;

    @Autowired
    PlayInfoService playInfoService;

    @Autowired
    ContributorService contributorService;
    @Autowired
    PredictionService predictionService;

    /**
     * Controller method for creating a new user
     *
     * @param user a new user object holding username and image path
     * @return the {@link ResponseEntity} with HTTP Status and User object
     */
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User _user = userService.addUser(user);
            template.convertAndSend("/topic/message", userService.getAllUsers().size());
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method for adding a new vote
     *
     *
     * @param vote the vote object holding user, answer and question information
     * @return the {@link ResponseEntity} with HTTP Status and {@link Vote} object
     */
    @PostMapping("/vote")
    public ResponseEntity<Vote> addVote(@RequestBody Vote vote) {
        try {
            Vote _vote = voteService.addVote(vote);
            return new ResponseEntity<>(_vote, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method to retrieve current status of the application
     * from PlayInfo object
     *
     * @return the app status
     */
    @GetMapping("/getAppStatus")
    public ResponseEntity<Boolean> getAppStatus() {
        try {
            Boolean isActive = playInfoService.getStatus();
            return new ResponseEntity<>(isActive, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method returning the result for a question by questionId
     * Sends a WebWocket message to show the result of the question to the user
     *
     * @param questionId the unique identifier for {@link Question}
     * @return the {@link ResponseEntity} with HTTP Status and {@link Result} object
     */
    @GetMapping("/result")
    public ResponseEntity<Result> getResult(@RequestParam("questionId") long questionId) {
        try {
            Result result = questionService.getResult(questionId);
            template.convertAndSend("/topic/result", result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method to get Final Result of voting for the user
     *
     * @param userId the unique identifier for {@link User}
     * @return the {@link ResponseEntity} with HTTP Status and {@link FinalResult} object
     */
    @GetMapping("/finalResult")
    public ResponseEntity<FinalResult> getFinalResult(@RequestParam("userId") long userId) {
        try {
            FinalResult finalResult = questionService.getFinalResults(userId);
            return new ResponseEntity<>(finalResult, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method to get all contributors with type 'Cast'
     *
     * @return the {@link ResponseEntity} with HTTP Status and List of Contributors
     */
    @GetMapping("/cast")
    public ResponseEntity<List<Contributor>> getCast() {
        try {
            List<Contributor> contributors = contributorService.getCast();
            return new ResponseEntity<>(contributors, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method to get all contributors with type 'Dev Team'
     *
     * @return the {@link ResponseEntity} with HTTP Status and List of Contributors
     */
    @GetMapping("/devTeam")
    public ResponseEntity<List<Contributor>> getDevTeam() {
        try {
            List<Contributor> contributors = contributorService.getDevTeam();
            return new ResponseEntity<>(contributors, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method to get current PlayInfo
     *
     * @return the {@link ResponseEntity} with HTTP Status and {@link PlayInfo} object
     */
    @GetMapping("/getPlayInfo")
    public ResponseEntity<PlayInfo> getPlayInfo() {
        try {
            PlayInfo playInfo = playInfoService.getPlayInfo();
            return new ResponseEntity<>(playInfo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method to get number of questions
     *
     * @return integer value for the number of questions
     */
    @GetMapping("/numberOfQuestions")
    public ResponseEntity<Integer> getNumberOfQuestions() {
        try {
            int numberOfQuestions = questionService.getNumberOfQuestions();
            return new ResponseEntity<>(numberOfQuestions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method to get predicted answer
     *
     * @param userId the unique identifier for {@link User}
     * @return boolean value for the predicted answer
     */
    @GetMapping("/predictedAnswer")
    public ResponseEntity<Integer> getPredictedAnswer(@RequestParam("userId") long userId) {
        try {
            /*int predictedAnswerNumber = predictionService.werePredictionsGenerated()
                    ? predictionService.getPredictionForUser(userId) : questionService.getPredictedAnswer(userId);*/
            return new ResponseEntity<>(predictionService.getPredictedAnswer(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
