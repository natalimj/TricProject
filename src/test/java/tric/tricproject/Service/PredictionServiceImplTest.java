package tric.tricproject.Service;

import org.junit.jupiter.api.AfterAll;
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
import tric.tricproject.Repository.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(PredictionServiceImpl.class)
class PredictionServiceImplTest {

    @TestConfiguration
    static class PredictionServiceImplTestContextConfiguration {
        @Bean
        public PredictionService predictionService() {
            return new PredictionServiceImpl();
        }
    }

    @Autowired
    PredictionService predictionService;

    @MockBean
    private VoteRepository voteRepository;
    @MockBean
    private UserRepository userRepository;
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
        when(userRepository.findAll()).thenReturn(users);

        Vote vote1 = new Vote(10,123, 100, 1);
        Vote vote2 = new Vote(11,124, 100, 2);
        Vote vote3 = new Vote(12,123, 100, 3);
        Vote vote4 = new Vote(13,124, 100, 4);
        List<Vote> votes1 = new ArrayList<>();
        List<Vote> votes2 = new ArrayList<>();
        votes1.add(vote1);
        votes1.add(vote3);
        votes2.add(vote2);
        votes2.add(vote4);
        when(voteRepository.findAllByUserId(123L)).thenReturn(votes1);
        when(voteRepository.findAllByUserId(124L)).thenReturn(votes2);

        Answer answer1 = new Answer(1,"Yes", "Pragmatic", "Conservative");
        Answer answer2 = new Answer(2,"No", "Idealist", "Progressive");
        Answer answer3 = new Answer(3,"Yes", "Pragmatic", "Conservative");
        Answer answer4 = new Answer(4,"No", "Idealist", "Progressive");
        when(answerRepository.findByAnswerId(1L)).thenReturn(answer1);
        when(answerRepository.findByAnswerId(2L)).thenReturn(answer2);
        when(answerRepository.findByAnswerId(3L)).thenReturn(answer3);
        when(answerRepository.findByAnswerId(4L)).thenReturn(answer4);

        predictionService.generatePredictions(2);
    }

    @Test
    void getPredictionForUser() {
        int label = predictionService.getPredictionForUser(123L);
        int label2 = predictionService.getPredictionForUser(124L);
        assertThat(label).isEqualTo(0);
        assertThat(label2).isEqualTo(1);
    }

    @Test
    void clearPredictions() {
        assertThat(predictionService.werePredictionsGenerated()).isEqualTo(true);
        predictionService.clearPredictions();
        assertThat(predictionService.werePredictionsGenerated()).isEqualTo(false);
    }

    @Test
    void werePredictionsGenerated() {
        assertThat(predictionService.werePredictionsGenerated()).isEqualTo(true);
    }
}