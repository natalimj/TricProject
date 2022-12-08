package tric.tricproject.controller;

import tric.tricproject.Controller.UserController;
import tric.tricproject.Model.*;
import tric.tricproject.Service.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @MockBean
    UserService userService;
    @MockBean
    VoteService voteService;
    @MockBean
    QuestionService questionService;
    @MockBean
    PlayInfoService playInfoService;
    @MockBean
    ContributorService contributorService;
    @MockBean
    PredictionService predictionService;
    @MockBean
    SimpMessagingTemplate template;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateUser() throws Exception {

        User user = new User(1, "TricUser","/image.jpg");

        mockMvc.perform(post("/userApi/user",user).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void shouldAddVote() throws Exception {

        Vote vote=  new Vote(1,55,2,22);

        mockMvc.perform(post("/userApi/vote",vote).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vote)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void shouldReturnAppStatus() throws Exception {
        boolean isActive = true;
        given(playInfoService.getStatus()).willReturn(isActive);
        mockMvc.perform(get("/userApi/getAppStatus").accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(isActive)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(isActive))
                .andDo(print());
    }

    @Test
    void shouldReturnResult() throws Exception {
        Question question= new Question(1,1,"question?");
        Answer answer1 = new Answer(1,"yes",question,"Conservative","Pragmatic");
        Answer answer2 = new Answer(2,"no",question,"Progressive","Idealist");
        List<Answer> answerList = new ArrayList<>();
        answerList.add(answer1);
        answerList.add(answer1);
        question.setAnswers(answerList);
        Result result = new Result(question,answer1,answer2,90,10);

        given(questionService.getResult(1)).willReturn(result);
        mockMvc.perform(get("/userApi/result").param("questionId", "1")
                        .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(result)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question.questionId").value(result.getQuestion().getQuestionId()))
                .andExpect(jsonPath("$.firstAnswer.answerId").value(result.getFirstAnswer().getAnswerId()))
                .andExpect(jsonPath("$.secondAnswer.answerId").value(result.getSecondAnswer().getAnswerId()))
                .andExpect(jsonPath("$.firstAnswerRate").value(result.getFirstAnswerRate()))
                .andExpect(jsonPath("$.secondAnswerRate").value(result.getSecondAnswerRate()))
                .andDo(print());
    }

    @Test
    void shouldReturnFinalResult() throws Exception {

        List<CategoryRate> categoryRateList = Arrays.asList(new CategoryRate("Category1","Category2",60),
                                                            new CategoryRate("Category3","Category4",50)  );
        List<FinalCategory> finalCategoryList = Arrays.asList(new FinalCategory("Theme1","Category1","Category3"),
                                                                new FinalCategory("Theme2","Category2","Category4"));
        FinalResult finalResult = new FinalResult(categoryRateList,finalCategoryList);

        given(questionService.getFinalResults(1)).willReturn(finalResult);
        mockMvc.perform(get("/userApi/finalResult").param("userId", "1")
                        .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(finalResult)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryRateList.size()").value(categoryRateList.size()))
                .andExpect(jsonPath("$.categoryRateList[0].category").value(categoryRateList.get(0).getCategory()))
                .andExpect(jsonPath("$.finalCategoryList.size()").value(finalCategoryList.size()))
                .andExpect(jsonPath("$.finalCategoryList[0].questionTheme").value(finalCategoryList.get(0).getQuestionTheme()))
                .andExpect(jsonPath("$.finalCategoryList[0].answerFirstCategory").value(finalCategoryList.get(0).getAnswerFirstCategory()))
                .andExpect(jsonPath("$.finalCategoryList[0].answerSecondCategory").value(finalCategoryList.get(0).getAnswerSecondCategory()))
                .andDo(print());
    }

    @Test
    void shouldReturnCast() throws Exception {
        List<Contributor> contributors = Arrays.asList(new Contributor(1,"Maria","Dancer","Cast"),
                new Contributor(2,"Mario","Actor","Cast"));

        given(contributorService.getCast()).willReturn(contributors);
        mockMvc.perform(get("/userApi/cast")
                        .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(contributors)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(contributors.size()))
                .andExpect(jsonPath("$[0].name").value(contributors.get(0).getName()))
                .andExpect(jsonPath("$[0].contributorId").value(contributors.get(0).getContributorId()))
                .andExpect(jsonPath("$[1].description").value(contributors.get(1).getDescription()))
                .andExpect(jsonPath("$[1].type").value(contributors.get(1).getType()))
                .andExpect(jsonPath("$[3]").doesNotExist())
                .andDo(print());
    }
    @Test
    void shouldReturnDevTeam() throws Exception {

        List<Contributor> contributors = Arrays.asList(new Contributor(1,"Bogdan","Developer","DevTeam"),
                new Contributor(2,"Daria","Developer","DevTeam"),
                new Contributor(3,"Natali","Developer","DevTeam"));

        given(contributorService.getDevTeam()).willReturn(contributors);
        mockMvc.perform(get("/userApi/devTeam")
                        .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(contributors)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(contributors.size()))
                .andExpect(jsonPath("$[0].name").value(contributors.get(0).getName()))
                .andExpect(jsonPath("$[0].contributorId").value(contributors.get(0).getContributorId()))
                .andExpect(jsonPath("$[1].description").value(contributors.get(1).getDescription()))
                .andExpect(jsonPath("$[1].type").value(contributors.get(1).getType()))
                .andExpect(jsonPath("$[3]").doesNotExist())
                .andDo(print());
    }
    @Test
    void shouldReturnPlayInfo() throws Exception {

        PlayInfo playInfo = new PlayInfo(1,"TRIC The Right Choice","TRIC will choose for you");

        given(playInfoService.getPlayInfo()).willReturn(playInfo);
        mockMvc.perform(get("/userApi/getPlayInfo")
                        .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(playInfo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playInfoId").value(playInfo.getPlayInfoId()))
                .andExpect(jsonPath("$.playInfoText").value(playInfo.getPlayInfoText()))
                .andExpect(jsonPath("$.finalResultText").value(playInfo.getFinalResultText()))
                .andDo(print());
    }

    @Test
    void shouldReturnNumberOfQuestions() throws Exception {
        given(questionService.getNumberOfQuestions()).willReturn(5);
        mockMvc.perform(get("/userApi/numberOfQuestions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(5))
                .andDo(print());
    }

    @Test
    void shouldReturnPredictedAnswer() throws Exception {
        given(predictionService.getPredictedAnswer(1)).willReturn(1);
        mockMvc.perform(get("/userApi/predictedAnswer").param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1))
                .andDo(print());
    }
}
