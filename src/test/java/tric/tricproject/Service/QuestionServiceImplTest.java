package tric.tricproject.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import tric.tricproject.Model.*;
import tric.tricproject.Repository.AnswerRepository;
import tric.tricproject.Repository.QuestionRepository;
import tric.tricproject.Repository.VoteRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(QuestionServiceImpl.class)
class QuestionServiceImplTest {

    @TestConfiguration
    static class QuestionServiceImplTestContextConfiguration {
        @Bean
        public QuestionService questionService() {
            return new QuestionServiceImpl();
        }
    }

    @Autowired
    QuestionService questionService;

    @MockBean
    private VoteRepository voteRepository;
    @MockBean
    private AnswerRepository answerRepository;
    @MockBean
    private QuestionRepository questionRepository;


    @BeforeEach
    void setUp() {
        User user1 = new User(123,"John");
        User user2 = new User(124, "Maria", "/this.jpg");
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        Vote vote1 = new Vote(10,123, 100, 1);
        Vote vote2 = new Vote(11,124, 100, 2);
        Vote vote3 = new Vote(12,123, 101, 3);
        Vote vote4 = new Vote(13,124, 101, 4);
        List<Vote> votes1 = new ArrayList<>();
        List<Vote> votes2 = new ArrayList<>();
        List<Vote> votes3 = new ArrayList<>();
        votes1.add(vote1);
        votes1.add(vote2);
        votes2.add(vote3);
        votes2.add(vote4);
        votes3.add(vote1);
        votes3.add(vote3);

        Answer answer1 = new Answer(1,"Yes", "Pragmatic", "Conservative");
        Answer answer2 = new Answer(2,"No", "Idealist", "Progressive");
        Answer answer3 = new Answer(3,"Yes", "Pragmatic", "Conservative");
        Answer answer4 = new Answer(4,"No", "Idealist", "Progressive");
        List<Answer> answers1 = new ArrayList<>();
        List<Answer> answers2 = new ArrayList<>();
        answers1.add(answer1);
        answers1.add(answer2);
        answers2.add(answer3);
        answers2.add(answer4);

        Question question1 = new Question(100,1, "Are you okay?", 30, "Awareness", answers1);
        Question question2 = new Question(101,2, "Are you not okay?", 30, "Awareness", answers2);
        Question question3 = new Question(102,3, "Hi?", 30, "Awareness", answers2);
        List<Question> questions = new ArrayList<>();
        questions.add(question1);
        questions.add(question2);
        questions.add(question3);

        doAnswer(invocation -> {
            questions.remove(question3);
            return questions;
        }).when(questionRepository).deleteById(102L);

        doAnswer(invocation -> {
            questions.clear();
            return questions;
        }).when(questionRepository).deleteAll();

        doAnswer(invocation -> {
            answers1.clear();
            return answers1;
        }).when(answerRepository).deleteAll();

        when(questionRepository.save(question1)).thenReturn(question1);
        when(questionRepository.findAll()).thenReturn(questions);
        when(questionRepository.findByQuestionNumber(1)).thenReturn(question1);
        when(questionRepository.findByQuestionId(100)).thenReturn(question1);
        when(questionRepository.findByQuestionId(101)).thenReturn(question2);
        when(questionRepository.findByQuestionId(102)).thenReturn(question3);
        when(questionRepository.save(question1)).thenReturn(question1);
        when(questionRepository.count()).thenReturn(3L);

        when(voteRepository.findAllByQuestionId(100L)).thenReturn(votes1);
        when(voteRepository.findAllByQuestionId(101L)).thenReturn(votes2);
        when(voteRepository.findAllByQuestionId(102L)).thenReturn(votes2);
        when(voteRepository.findAllByUserId(123)).thenReturn(votes3);

        when(answerRepository.findByAnswerId(1)).thenReturn(answer1);
        when(answerRepository.findByAnswerId(3)).thenReturn(answer3);
    }

    @Test
    void deleteQuestionById() {
        List<Question> questions = questionService.getAllQuestions();
        assertThat(questions.size()).isEqualTo(3);

        questionService.deleteQuestionById(102L);

        questions = questionService.getAllQuestions();
        assertThat(questions.size()).isEqualTo(2);
        assertThat(questions.get(0).getQuestionId()).isEqualTo(100);
        assertThat(questions.get(1).getQuestionId()).isEqualTo(101);
    }

    @Test
    void getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        assertThat(questions.size()).isEqualTo(3);
        assertThat(questions.get(0).getQuestionId()).isEqualTo(100);
        assertThat(questions.get(1).getQuestionId()).isEqualTo(101);
        assertThat(questions.get(2).getQuestionId()).isEqualTo(102);
    }

    @Test
    void getQuestionByNumber() {
        Question question = questionService.getQuestionByNumber(1);

        assertThat(question.getQuestionId()).isEqualTo(100);
    }

    @Test
    void getResult() {
        Result result = questionService.getResult(100);
        assertThat(result.getQuestion().getQuestionId()).isEqualTo(100);
        assertThat(result.getFirstAnswerRate()).isEqualTo(50);
        assertThat(result.getFirstAnswer().getAnswerText()).isEqualTo("Yes");
    }

    @Test
    void addQuestionTime() {
        Question question = questionService.addQuestionTime(100, 10);
        assertThat(question.getTime()).isEqualTo(10);
    }

    @Test
    void getResultListJson() {
        String list = questionService.getResultListJson();
        assertThat(list).isEqualTo("[{\"questionNumber\":1,\"questionText\":\"Are you okay?\",\"firstAnswer\":\"Yes\",\"firstAnswerRate\":50.0,\"secondAnswer\":\"No\",\"secondAnswerRate\":50.0},{\"questionNumber\":2,\"questionText\":\"Are you not okay?\",\"firstAnswer\":\"Yes\",\"firstAnswerRate\":50.0,\"secondAnswer\":\"No\",\"secondAnswerRate\":50.0},{\"questionNumber\":3,\"questionText\":\"Hi?\",\"firstAnswer\":\"Yes\",\"firstAnswerRate\":50.0,\"secondAnswer\":\"No\",\"secondAnswerRate\":50.0}]");
    }

    @Test
    void deleteAllQuestions() {
        questionService.deleteAllQuestions();
        assertThat(questionService.getAllQuestions().size()).isEqualTo(0);
        assertThat(answerRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    void getFinalResults() {
        FinalResult finalResult = questionService.getFinalResults(123);
        assertThat(finalResult.getCategoryRateList().get(0).getCategory()).isEqualTo("Conservative");
        assertThat(finalResult.getCategoryRateList().get(0).getRate()).isEqualTo(100);
        assertThat(finalResult.getCategoryRateList().get(1).getCategory()).isEqualTo("Pragmatic");
        assertThat(finalResult.getCategoryRateList().get(1).getRate()).isEqualTo(100);
    }

    @Test
    void editQuestion() {
        Answer answer1 = new Answer(1,"Yes", "Pragmatic", "Conservative");
        Answer answer2 = new Answer(2,"No", "Idealist", "Progressive");
        List<Answer> answers1 = new ArrayList<>();
        answers1.add(answer1);
        answers1.add(answer2);
        Question question1 = new Question(100,1, "Are you okay?", 30, "Awareness", answers1);

        Question result = questionService.editQuestion(question1);
        assertThat(result.getQuestionId()).isEqualTo(question1.getQuestionId());
    }

    @Test
    void getNumberOfQuestions() {
        assertThat(questionService.getNumberOfQuestions()).isEqualTo(3);
    }
}