package tric.tricproject.Controller;

import tric.tricproject.Model.Question;
import tric.tricproject.Model.Result;
import tric.tricproject.Service.QuestionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/questionApi")
public class QuestionController {
    private final Logger log = LoggerFactory.getLogger(QuestionController.class);
    @Autowired
    QuestionService questionService;
    @Autowired
    SimpMessagingTemplate template;

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
            log.info("Internal Server Error - QuestionController/getQuestion",e);
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
            log.error("Internal Server Error - QuestionController/getResult",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addQuestion")
    public ResponseEntity<Question> addQuestion(@RequestParam String question,@RequestParam String firstAnswer,@RequestParam String secondAnswer) {
        try {
            Question _question = questionService.addQuestion(question,firstAnswer,secondAnswer);
            log.info("A new question has been added. Question Id:"+_question.getQuestionId());
            return new ResponseEntity<>(_question, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Internal Server Error - QuestionController/addQuestion",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addQuestion2")
    public ResponseEntity<Question> addQuestion2(@RequestBody Question question) {
        try {
            Question _question = questionService.addQuestion(question);
            log.info("A new question has been added. Question Id:"+_question.getQuestionId());
            return new ResponseEntity<>(_question, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Internal Server Error - QuestionController/addQuestion",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editQuestion")
    public ResponseEntity<Question> editQuestion(@RequestBody Question question) {
        try {
            Question _question = questionService.editQuestion(question);
            return new ResponseEntity<>(_question, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Internal Server Error - QuestionController/editQuestion",e);
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



}
