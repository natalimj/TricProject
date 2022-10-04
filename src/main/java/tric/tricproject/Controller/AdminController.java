package tric.tricproject.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import tric.tricproject.Model.Question;
import tric.tricproject.Model.Result;
import tric.tricproject.Model.Status;
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
    PlayResultService playResultService;

    @Autowired
    StatusService statusService;

    @Autowired
    SimpMessagingTemplate template;

    @PostMapping("/endSession")
    public ResponseEntity<Status> endSession() {
        try {
            playResultService.createPlayResults();   //TODO: download play result file
            //delete all users and votes
            userService.deleteAllUsers();
            voteService.deleteAllVotes();
            Status status = statusService.setAppStatus(false);
            template.convertAndSend("/topic/status", false);
            return new ResponseEntity<>(status, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/question")
    public ResponseEntity<Question> getQuestion(@RequestParam("questionNumber") int questionNumber) {
        try {
            Question question = questionService.getQuestionByNumber(questionNumber);
            if( question != null){
                template.convertAndSend("/topic/question", question);
                return new ResponseEntity<>(question, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/result")  //TODO: maybe it can be only a service method - call with timer?
    public ResponseEntity<Result> getResult(@RequestParam("questionId") long questionId) {

        try {
            Result result = questionService.getResult(questionId);
            template.convertAndSend("/topic/result",result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addQuestion")
    public ResponseEntity<Question> addQuestion(@RequestParam("questionText") String questionText,
                                                @RequestParam("firstAnswer") String firstAnswer,
                                                @RequestParam("secondAnswer") String secondAnswer) {
        try {
            Question _question = questionService.addQuestion(questionText,firstAnswer,secondAnswer);
            return new ResponseEntity<>(_question, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/editQuestion")
    public ResponseEntity<Question> editQuestion(@RequestParam("questionText") String questionText,
                                                 @RequestParam("firstAnswer") String firstAnswer,
                                                 @RequestParam("secondAnswer") String secondAnswer,
                                                 @RequestParam("questionId") long questionId) {
        try {
            Question _question = questionService.editQuestion(questionId,questionText,firstAnswer,secondAnswer);
            return new ResponseEntity<>(_question, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/deleteQuestion")
    public ResponseEntity<Long> deleteQuestion(@RequestParam Long questionId) {
        try {
            questionService.deleteQuestionById(questionId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
    @PostMapping("/showFinalResult")
    public ResponseEntity<Void> showFinalResult() {
        template.convertAndSend("/topic/finalResult", true);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


