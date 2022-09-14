package tric.tricproject.Controller;

import tric.tricproject.Model.PlayResult;
import tric.tricproject.Model.Vote;
import tric.tricproject.Service.PlayResultService;
import tric.tricproject.Service.UserService;
import tric.tricproject.Service.VoteService;
import tric.tricproject.Util.PlayResultExcelExporter;
import tric.tricproject.Util.VoteExcelExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/adminApi")
public class AdminController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;
    @Autowired
    VoteService voteService;

    @Autowired
    PlayResultService playResultService;

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

    @PostMapping("/endSession")
    public void endSession() {
        playResultService.createPlayResults();

        //delete all users and votes
        userService.deleteAllUsers();
        voteService.deleteAllVotes();

        log.info("session ended...");
    }
}
