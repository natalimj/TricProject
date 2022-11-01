package tric.tricproject.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import tric.tricproject.Model.*;
import tric.tricproject.Service.*;

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
    SimpMessagingTemplate template;

    @GetMapping("/endSession")
    public ResponseEntity<String> endSession() {
        try {
            String resultJson =questionService.getResultListJson();
            //delete all users and votes
            userService.deleteAllUsers();
            voteService.deleteAllVotes();
            statusService.setAppStatus(false);
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
    public ResponseEntity<Question> addQuestion(@RequestParam("questionText") String questionText,
                                                @RequestParam("firstAnswer") String firstAnswer,
                                                @RequestParam("secondAnswer") String secondAnswer,
                                                @RequestParam("theme") String theme,
                                                @RequestParam("firstCategory1") String firstCategory1,
                                                @RequestParam("secondCategory1") String secondCategory1,
                                                @RequestParam("firstCategory2") String firstCategory2,
                                                @RequestParam("secondCategory2") String secondCategory2) {
        try {
            Question _question = questionService.addQuestion(questionText, firstAnswer, secondAnswer, theme, firstCategory1,secondCategory1,firstCategory2,secondCategory2);
            return new ResponseEntity<>(_question, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/editQuestion")
    public ResponseEntity<Question> editQuestion(@RequestParam("questionText") String questionText,
                                                 @RequestParam("firstAnswer") String firstAnswer,
                                                 @RequestParam("secondAnswer") String secondAnswer,
                                                 @RequestParam("questionId") long questionId,
                                                 @RequestParam("theme") String theme,
                                                 @RequestParam("firstCategory1") String firstCategory1,
                                                 @RequestParam("secondCategory1") String secondCategory1,
                                                 @RequestParam("firstCategory2") String firstCategory2,
                                                 @RequestParam("secondCategory2") String secondCategory2) {
        try {
            Question _question = questionService.editQuestion(questionId, questionText, firstAnswer, secondAnswer, theme, firstCategory1,secondCategory1,firstCategory2,secondCategory2);
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
            template.convertAndSend("/topic/status",true);
            return new ResponseEntity<>(status, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deactivate")
    public ResponseEntity<Status> setInactive() {
        try {
            Status status = statusService.setAppStatus(false);
            template.convertAndSend("/topic/status",false);
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
            Question _question = questionService.addQuestionTime(questionId,time);
            return new ResponseEntity<>(_question, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/numberOfQuestions")
    public ResponseEntity<Integer> getNumberOfQuestions() {
        try {
            int numberOfQuestions = questionService.getAllQuestions().size();
            return new ResponseEntity<>(numberOfQuestions , HttpStatus.OK);
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

}


