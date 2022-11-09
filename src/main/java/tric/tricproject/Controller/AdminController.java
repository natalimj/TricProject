package tric.tricproject.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import tric.tricproject.Model.*;
import tric.tricproject.Service.*;

import java.util.HashMap;
import java.util.List;

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
    StatusService statusService;

    @Autowired
    ContributorService contributorService;

    @Autowired
    PlayInfoService playInfoService;

    @Autowired
    PredictionService predictionService;

    @Autowired
    SimpMessagingTemplate template;

    @GetMapping("/endSession")
    public ResponseEntity<String> endSession() {
        try {
            String resultJson = questionService.getResultListJson();
            //delete all users and votes
            userService.deleteAllUsers();
            voteService.deleteAllVotes();
            statusService.setAppStatus(false);
            predictionService.clearPredictions();
            template.convertAndSend("/topic/status", false);
            return new ResponseEntity<>(resultJson, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/question")
    public ResponseEntity<Question> getQuestion(@RequestParam("questionNumber") int questionNumber) {
        try {
            Question question = questionService.getQuestionByNumber(questionNumber);
            if (question != null) {
                int numberOfQuestions = questionService.getNumberOfQuestions();
                if (questionNumber == numberOfQuestions) {
                    predictionService.generatePredictions(numberOfQuestions - 1);
                }
                return new ResponseEntity<>(question, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

    @PostMapping("/addQuestion")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        try {
            Question _question = questionService.addQuestion(question);
            return new ResponseEntity<>(_question, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/editQuestion")
    public ResponseEntity<Question> editQuestion(@RequestBody Question question) {

        try {
            Question _question = questionService.editQuestion(question);
            return new ResponseEntity<>(_question, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/deleteQuestion")
    public ResponseEntity<Long> deleteQuestion(@RequestParam Long questionId) {
        try {
            questionService.deleteQuestionById(questionId);
            return new ResponseEntity<>(questionId, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/deleteQuestions")
    public ResponseEntity deleteAllQuestions() {
        try {
            questionService.deleteAllQuestions();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            List<Question> questions = questionService.getAllQuestions();
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/activate")
    public ResponseEntity<Status> setActive() {
        try {
            Status status = statusService.setAppStatus(true);
            template.convertAndSend("/topic/status", true);
            return new ResponseEntity<>(status, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deactivate")
    public ResponseEntity<Status> setInactive() {
        try {
            Status status = statusService.setAppStatus(false);
            template.convertAndSend("/topic/status", false);
            return new ResponseEntity<>(status, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/showFinalResult")
    public ResponseEntity<Void> showFinalResult() {
        template.convertAndSend("/topic/finalResult", true);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/addQuestionTime")
    public ResponseEntity<Question> addQuestionTime(@RequestParam("questionId") long questionId, @RequestParam("time") int time) {
        try {
            Question _question = questionService.addQuestionTime(questionId, time);
            return new ResponseEntity<>(_question, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/contributor")
    public ResponseEntity<Contributor> addContributor(@RequestBody Contributor contributor) {
        try {
            Contributor _contributor = contributorService.addContributor(contributor);
            return new ResponseEntity<>(_contributor, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/editContributor")
    public ResponseEntity<Contributor> editContributor(@RequestBody Contributor contributor) {
        try {
            Contributor _contributor = contributorService.editContributor(contributor);
            return new ResponseEntity<>(_contributor, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/contributors")
    public ResponseEntity<List<Contributor>> getAllContributors() {
        try {
            List<Contributor> contributors = contributorService.getAllContributors();
            return new ResponseEntity<>(contributors, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/deleteContributor")
    public ResponseEntity<Long> deleteContributor(@RequestParam Long contributorId) {
        try {
            contributorService.deleteContributorById(contributorId);
            return new ResponseEntity<>(contributorId, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/playInfo")
    public ResponseEntity<PlayInfo> editPlayInfo(@RequestBody PlayInfo playInfo) {
        try {
            PlayInfo _playInfo = playInfoService.editPlayInfo(playInfo);
            return new ResponseEntity<>(_playInfo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

    @GetMapping("/numberOfQuestions")
    public ResponseEntity<Integer> getNumberOfQuestions() {
        try {
            int numberOfQuestions = questionService.getNumberOfQuestions();
            return new ResponseEntity<>(numberOfQuestions , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/startCountdown")
    public ResponseEntity<Integer> startCountdown(@RequestParam("timer") int timer) {
        try {
            template.convertAndSend("/topic/timer", timer);
            return new ResponseEntity<>(timer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


