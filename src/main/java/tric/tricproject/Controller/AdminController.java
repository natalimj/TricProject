package tric.tricproject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import tric.tricproject.Model.Question;
import tric.tricproject.Model.Result;
import tric.tricproject.Model.Status;
import tric.tricproject.Service.*;
import tric.tricproject.Util.PlayResultJsonExporter;

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

    /*
    //do we need this?
    @GetMapping("/exportExcel2")
    public void exportVoteToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=votes_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Vote> votes = voteService.getAllVotes();
        VoteExcelExporter excelExporter = new VoteExcelExporter(votes);
        log.info("Exporting an excel file : "+"votes_" + currentDateTime + ".xlsx");
        excelExporter.export(response);
    }

    @GetMapping("/exportExcel")
    public void exportPlayResultToExcel(HttpServletResponse response,@RequestParam Long playResultId) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=play_results_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<PlayResult> playResults = playResultService.getAllPlayResults(); // TODO :get by date or Id?
        PlayResultExcelExporter excelExporter = new PlayResultExcelExporter(playResults);
        log.info("Exporting an excel file : "+"play_results_" + currentDateTime + ".xlsx");
        excelExporter.export(response);
    }
*/
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



}


