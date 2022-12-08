package tric.tricproject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import tric.tricproject.Model.*;
import tric.tricproject.Service.*;

import java.util.List;

/**
 * Controller class for handling requests from the Admin
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0, October 2022
 */
@CrossOrigin
@RestController
@RequestMapping("/adminApi")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    VoteService voteService;

    @Autowired
    QuestionService questionService;

    @Autowired
    ContributorService contributorService;

    @Autowired
    PlayInfoService playInfoService;
    /*@Autowired
    PredictionService predictionService;*/

    @Autowired
    SimpMessagingTemplate template;

    /**
     * Controller method to end the active session
     * deletes all users and votes from the database
     * sets the application status to false
     * sends a WebSocket message to inform the users about status change
     *
     * @return Json string generated from the result list
     */
    @GetMapping("/endSession")
    public ResponseEntity<String> endSession() {
        try {
            String resultJson = questionService.getResultListJson();
            userService.deleteAllUsers();
            voteService.deleteAllVotes();
            playInfoService.setAppStatus(false);
            //predictionService.clearPredictions();
            template.convertAndSend("/topic/status", false);
            return new ResponseEntity<>(resultJson, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method to retrieve the question by question number
     *
     * @param questionNumber the question number
     * @return Response entity with HttpStatus and Question object
     */
    @GetMapping("/question")
    public ResponseEntity getQuestion(@RequestParam("questionNumber") int questionNumber) {
        try {
            Question question = questionService.getQuestionByNumber(questionNumber);
            if (question != null) {
                /*int numberOfQuestions = questionService.getNumberOfQuestions();
                if (questionNumber == numberOfQuestions) {
                    predictionService.generatePredictions(numberOfQuestions - 1);
                }*/
                return new ResponseEntity<>(question, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Controller method to send a WebSocket message to the users
     * for showing the Question retrieved by question number
     *
     * @param questionNumber the question number
     * @return Response entity with HttpStatus
     */
    @GetMapping("/showQuestion")
    public ResponseEntity showQuestion(@RequestParam("questionNumber") int questionNumber) {
        try {
            Question question = questionService.getQuestionByNumber(questionNumber);
            if (question != null) {
                template.convertAndSend("/topic/question", question);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method to add a new {@link Question}
     *
     * @param question {@link Question} object to be added to the database
     * @return the {@link ResponseEntity} with HTTP Status and {@link Question} object
     */
    @PostMapping("/addQuestion")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        try {
            Question _question = questionService.addQuestion(question);
            return new ResponseEntity<>(_question, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method to edit  {@link Question}
     *
     * @param question {@link Question} object to be edited
     * @return the {@link ResponseEntity} with HTTP Status and {@link Question} object
     */
    @PatchMapping("/editQuestion")
    public ResponseEntity<Question> editQuestion(@RequestBody Question question) {
        try {
            Question _question = questionService.editQuestion(question);
            return new ResponseEntity<>(_question, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method to delete {@link Question} by questionId
     *
     * @param questionId unique identifier for {@link Question} object
     * @return the {@link ResponseEntity} with HttpStatus and long value for id of deleted {@link Question}
     */
    @DeleteMapping(value = "/deleteQuestion")
    public ResponseEntity<Long> deleteQuestion(@RequestParam Long questionId) {
        try {
            questionService.deleteQuestionById(questionId);
            return new ResponseEntity<>(questionId, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method to delete all {@link Question}
     *
     * @return the {@link ResponseEntity} with HTTP Status
     */
    @DeleteMapping(value = "/deleteQuestions")
    public ResponseEntity deleteAllQuestions() {
        try {
            questionService.deleteAllQuestions();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method to retrieve all Questions
     *
     * @return the {@link ResponseEntity} with HTTP Status and a list of {@link Question}
     */
    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            List<Question> questions = questionService.getAllQuestions();
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method to set the application status to true
     * sends a WebSocket message to inform the user about status change
     *
     * @return the {@link ResponseEntity} with HTTP Status and {@link Status}
     */
    @PostMapping("/activate")
    public ResponseEntity<Status> setActive() {
        try {
            playInfoService.setAppStatus(true);
            template.convertAndSend("/topic/status", true);
            return new ResponseEntity<>(new Status(true), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method to set the application status to false
     * sends a WebSocket message to inform the user about status change
     *
     * @return the {@link ResponseEntity} with HTTP Status and {@link Status}
     */
    @PostMapping("/deactivate")
    public ResponseEntity<Status> setInactive() {
        try {
            playInfoService.setAppStatus(false);
            template.convertAndSend("/topic/status", false);
            return new ResponseEntity<>(new Status(false), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method to send a WebSocket message
     * from the admin to the user
     * this message is sent when it is time to get final result
     *
     * @return the {@link ResponseEntity} with HTTP Status
     */
    @PostMapping("/showFinalResult")
    public ResponseEntity<Void> showFinalResult() {
        template.convertAndSend("/topic/finalResult", true);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Controller method to add a time to {@link Question}
     *
     * @param questionId unique identifier for {@link Question}
     * @param time screen time for the question
     * @return the {@link ResponseEntity} with HTTP Status and {@link Question}
     */
    @PostMapping("/addQuestionTime")
    public ResponseEntity<Question> addQuestionTime(@RequestParam("questionId") long questionId, @RequestParam("time") int time) {
        try {
            Question _question = questionService.addQuestionTime(questionId, time);
            return new ResponseEntity<>(_question, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method for adding a new Contributor
     *
     * @param contributor the contributor to be added to the database
     * @return the {@link ResponseEntity} with HTTP Status and {@link Contributor}
     */
    @PostMapping("/contributor")
    public ResponseEntity<Contributor> addContributor(@RequestBody Contributor contributor) {
        try {
            contributor.setPlayInfo(new PlayInfo(1L));
            Contributor _contributor = contributorService.addContributor(contributor);
            return new ResponseEntity<>(_contributor, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method to edit a Contributor
     *
     * @param contributor Contributor to be edited
     * @return the {@link ResponseEntity} with HTTP Status and {@link Contributor}
     */
    @PatchMapping("/editContributor")
    public ResponseEntity<Contributor> editContributor(@RequestBody Contributor contributor) {
        try {
            contributor.setPlayInfo(new PlayInfo(1L));
            Contributor _contributor = contributorService.editContributor(contributor);
            return new ResponseEntity<>(_contributor, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method to get all Contributors
     *
     * @return the {@link ResponseEntity} with HTTP Status and a list of {@link Contributor}
     */
    @GetMapping("/contributors")
    public ResponseEntity<List<Contributor>> getAllContributors() {
        try {
            List<Contributor> contributors = contributorService.getAllContributors();
            return new ResponseEntity<>(contributors, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method to delete a contributor by contributorId
     *
     * @param contributorId unique identifier for {@link Contributor}
     * @return the {@link ResponseEntity} with HTTP Status and long value representing contributorId
     */
    @DeleteMapping(value = "/deleteContributor")
    public ResponseEntity<Long> deleteContributor(@RequestParam Long contributorId) {
        try {
            contributorService.deleteContributorById(contributorId);
            return new ResponseEntity<>(contributorId, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method to edit {@link PlayInfo}
     *
     * @param playInfo {@link PlayInfo} to be edited
     * @return the {@link ResponseEntity} with HTTP Status and {@link PlayInfo}
     */
    @PatchMapping("/playInfo")
    public ResponseEntity<PlayInfo> editPlayInfo(@RequestBody PlayInfo playInfo) {
        try {
            PlayInfo _playInfo = playInfoService.editPlayInfo(playInfo);
            return new ResponseEntity<>(_playInfo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method to send a WebSocket message
     * this message is sent when it is time to show a question on admin/result screen
     *
     * @param questionNumber the question number
     * @return the {@link ResponseEntity} with HTTP Status and a list of {@link Question}
     */
    @GetMapping("/displayQuestion")
    public ResponseEntity<Question> displayQuestion(@RequestParam("questionNumber") int questionNumber) {
        try {
            Question question = questionService.getQuestionByNumber(questionNumber);
            template.convertAndSend("/topic/adminQuestion", question);
            return new ResponseEntity<>(question, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method to get the number of questions.
     *
     * @return integer value representing the number of questions
     */
    @GetMapping("/numberOfQuestions")
    public ResponseEntity<Integer> getNumberOfQuestions() {
        try {
            int numberOfQuestions = questionService.getNumberOfQuestions();
            return new ResponseEntity<>(numberOfQuestions , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method to get all users
     *
     * @return the {@link ResponseEntity} with HTTP Status and a list of {@link User}
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method to send a WebSocket message
     * this message is sent when it is time to start countdown on admin/result screen
     *
     * @param timer the timer
     * @return the {@link ResponseEntity} with HTTP Status
     */
    @GetMapping("/startCountdown")
    public ResponseEntity<Integer> startCountdown(@RequestParam("timer") int timer) {
        try {
            template.convertAndSend("/topic/timer", timer);
            return new ResponseEntity<>(timer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Controller method to send a WebSocket message
     * this message is sent when it is time to clear the admin/result screen
     *
     * @return the {@link ResponseEntity} with HTTP Status
     */
    @PostMapping("/cleanResultPage")
    public ResponseEntity cleanResultPage() {
        try {
            template.convertAndSend("/topic/cleanPage",true);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


