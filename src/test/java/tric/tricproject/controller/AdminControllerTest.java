package tric.tricproject.controller;

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
import tric.tricproject.Controller.AdminController;
import tric.tricproject.Model.*;
import tric.tricproject.Service.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AdminController.class)
public class AdminControllerTest {

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
    void shouldEndSession() throws Exception {

        String result = "JsonString";
        given(questionService.getResultListJson()).willReturn(result);
        mockMvc.perform(get("/adminApi/endSession"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(result))
                .andDo(print());
    }

    @Test
    void shouldReturnQuestion() throws Exception {

        Question question= new Question(1,1,"question?");
        Answer answer1 = new Answer(1,"yes",question,"Conservative","Pragmatic");
        Answer answer2 = new Answer(2,"no",question,"Progressive","Idealist");
        List<Answer> answerList = new ArrayList<>();
        answerList.add(answer1);
        answerList.add(answer2);
        question.setAnswers(answerList);
        given(questionService.getQuestionByNumber(1)).willReturn(question);

        mockMvc.perform(get("/adminApi/question").param("questionNumber", "1")
                        .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(question)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.questionId").value(question.getQuestionId()))
                .andExpect(jsonPath("$.questionText").value(question.getQuestionText()))
                .andExpect(jsonPath("$.answers.size()").value(question.getAnswers().size()))
                .andExpect(jsonPath("$.answers[0].answerId").value(question.getAnswers().get(0).getAnswerId()))
                .andExpect(jsonPath("$.answers[1].answerId").value(question.getAnswers().get(1).getAnswerId()))
                .andDo(print());

        given(questionService.getQuestionByNumber(2)).willReturn(null);

        mockMvc.perform(get("/adminApi/showQuestion").param("questionNumber", "2")
                        .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(question)))
                .andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    void shouldShowQuestion() throws Exception {
        Question question= new Question(1,1,"question?");
        Answer answer1 = new Answer(1,"yes",question,"Conservative","Pragmatic");
        Answer answer2 = new Answer(2,"no",question,"Progressive","Idealist");
        List<Answer> answerList = new ArrayList<>();
        answerList.add(answer1);
        answerList.add(answer2);
        question.setAnswers(answerList);
      given(questionService.getQuestionByNumber(1)).willReturn(question);

        mockMvc.perform(get("/adminApi/showQuestion").param("questionNumber", "1")
                        .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(question)))
                .andExpect(status().isOk())
                .andDo(print());

        given(questionService.getQuestionByNumber(2)).willReturn(null);

        mockMvc.perform(get("/adminApi/showQuestion").param("questionNumber", "2")
                        .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(question)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void shouldAddQuestion() throws Exception {
        Question question= new Question(1,1,"question?");
        given(questionService.addQuestion(question)).willReturn(question);
        mockMvc.perform(post("/adminApi/addQuestion",question).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(question)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void shouldEditQuestion() throws Exception {
        Question updatedQuestion= new Question(1,1,"question?");
        given(questionService.editQuestion(updatedQuestion)).willReturn(updatedQuestion);

        mockMvc.perform(patch("/adminApi/editQuestion").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedQuestion)))
                .andExpect(status().isCreated())
                .andDo(print());

    }

    @Test
    void shouldDeleteQuestion() throws Exception {
        doNothing().when(questionService).deleteQuestionById(1L);
        mockMvc.perform(delete("/adminApi/deleteQuestion").param("questionId", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void shouldDeleteAllQuestions() throws Exception {
        doNothing().when(questionService).deleteAllQuestions();
        mockMvc.perform(delete("/adminApi/deleteQuestions"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void shouldReturnAllQuestions() throws Exception {

        List<Question> questions= new ArrayList<>(
                Arrays.asList(new Question(1, 1,"Question1"),
                        new Question(2, 2,"Question2"),
                        new Question(3, 3,"Question3")));

        when(questionService.getAllQuestions()).thenReturn(questions);
        mockMvc.perform(get("/adminApi/questions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(questions.size()))
                .andExpect(jsonPath("$[0].questionId").value(questions.get(0).getQuestionId()))
                .andExpect(jsonPath("$[1].questionId").value(questions.get(1).getQuestionId()))
                .andExpect(jsonPath("$[0].questionNumber").value(questions.get(0).getQuestionNumber()))
                .andExpect(jsonPath("$[0].questionText").value(questions.get(0).getQuestionText()))
                .andDo(print());
    }

    @Test
    void shouldSetStatusActive() throws Exception {
        PlayInfo playInfo = new PlayInfo();
        playInfo.setActive(true);
        given(playInfoService.setAppStatus(true)).willReturn(playInfo);
        mockMvc.perform(post("/adminApi/activate"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isActive").value(playInfo.isActive()))
                .andDo(print());
    }
    @Test
    void shouldSetStatusInactive() throws Exception {
        PlayInfo playInfo = new PlayInfo();
        playInfo.setActive(false);
        given(playInfoService.setAppStatus(false)).willReturn(playInfo);
        mockMvc.perform(post("/adminApi/deactivate"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isActive").value(playInfo.isActive()))
                .andDo(print());

    }
    @Test
    void shouldShowFinalResult() throws Exception {
        mockMvc.perform(post("/adminApi/showFinalResult"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void shouldAddQuestionTime() throws Exception {
        Question question = new Question(1,1,"Question 1",30, "Theme 1");
        given(questionService.addQuestionTime(1,30)).willReturn(question);
        mockMvc.perform(post("/adminApi/addQuestionTime")
                        .param("questionId", "1")
                        .param("time", "30"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.questionId").value(question.getQuestionId()))
                .andExpect(jsonPath("$.time").value(question.getTime()))
                .andDo(print());
    }

    @Test
    void shouldAddContributor() throws Exception {
        Contributor contributor = new Contributor(1,"Maria","Dancer","Cast");
        given(contributorService.addContributor(contributor)).willReturn(contributor);
        mockMvc.perform(post("/adminApi/contributor",contributor).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contributor)))
                .andExpect(status().isCreated())
                .andDo(print());
    }
    @Test
    void shouldEditContributor() throws Exception {
        Contributor updatedContributor = new Contributor(1,"Mario","Actor","Cast");
        given(contributorService.addContributor(updatedContributor )).willReturn(updatedContributor );
        mockMvc.perform(post("/adminApi/contributor",updatedContributor).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedContributor)))
                .andExpect(status().isCreated())
                .andDo(print());
    }
    @Test
    void shouldReturnAllContributors() throws Exception {
        List<Contributor> contributors = Arrays.asList(new Contributor(1,"Maria","Dancer","Cast"),
                new Contributor(2,"Mario","Actor","Cast"));
        given(contributorService.getAllContributors()).willReturn(contributors);
        mockMvc.perform(get("/adminApi/contributors")
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
    void shouldDeleteContributor() throws Exception {
        doNothing().when(contributorService).deleteContributorById(1L);
        mockMvc.perform(delete("/adminApi/deleteContributor").param("contributorId", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    void shouldEditPlayInfo() throws Exception {
        PlayInfo updatedPlayInfo = new PlayInfo(1,"TRIC Play Info Updated","TRIC description");
        given(playInfoService.editPlayInfo(updatedPlayInfo)).willReturn(updatedPlayInfo);

        mockMvc.perform(patch("/adminApi/playInfo",updatedPlayInfo).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPlayInfo)))
                .andExpect(status().isCreated())
                .andDo(print());
    }
    @Test
    void shouldDisplayQuestion() throws Exception {
        Question question= new Question(1,1,"question?");
        Answer answer1 = new Answer(1,"yes",question,"Conservative","Pragmatic");
        Answer answer2 = new Answer(2,"no",question,"Progressive","Idealist");
        List<Answer> answerList = new ArrayList<>();
        answerList.add(answer1);
        answerList.add(answer1);
        question.setAnswers(answerList);
        given(questionService.getQuestionByNumber(1)).willReturn(question);

        mockMvc.perform(get("/adminApi/displayQuestion").param("questionNumber", "1")
                        .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(question)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.questionId").value(question.getQuestionId()))
                .andExpect(jsonPath("$.questionText").value(question.getQuestionText()))
                .andExpect(jsonPath("$.answers.size()").value(question.getAnswers().size()))
                .andExpect(jsonPath("$.answers[0].answerId").value(question.getAnswers().get(0).getAnswerId()))
                .andExpect(jsonPath("$.answers[0].answerId").value(question.getAnswers().get(1).getAnswerId()))
                .andDo(print());
    }

    @Test
    void shouldReturnNumberOfQuestions() throws Exception {
        given(questionService.getNumberOfQuestions()).willReturn(5);
        mockMvc.perform(get("/adminApi/numberOfQuestions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(5))
                .andDo(print());
    }
    @Test
    void shouldReturnAllUsers() throws Exception {
        List<User> users= new ArrayList<>(
                Arrays.asList(new User(1, "TricUser","/image.jpg"),
                        new User(2, "TricUser2","/image2.jpg"),
                        new User(3, "TricUser3","/image3.jpg")));

        when(userService.getAllUsers()).thenReturn(users);
        mockMvc.perform(get("/adminApi/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(users.size()))
                .andExpect(jsonPath("$[0].username").value("TricUser"))
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$[1].userId").value(2))
                .andExpect(jsonPath("$[2].userId").value(users.get(2).getUserId()))
                .andDo(print());
    }

    @Test
    void shouldStartCountdown() throws Exception {
        mockMvc.perform(get("/adminApi/startCountdown").param("timer", "30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(30))
                .andDo(print());
    }
}
