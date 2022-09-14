package tric.tricproject.Controller;

import tric.tricproject.Model.Question;
import tric.tricproject.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/testApi")
public class TestController {
    @Autowired
    QuestionService questionService;

    @PostMapping("/addQuestion")
    public ResponseEntity<Question> addQuestion() {
        try {
            Question _question = questionService.addQuestion("Who","Tom","Bob");
            return new ResponseEntity<>(_question, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
