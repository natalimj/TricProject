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
import tric.tricproject.Model.Vote;
import tric.tricproject.Repository.UserRepository;
import tric.tricproject.Repository.VoteRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(VoteServiceImpl.class)
class VoteServiceImplTest {

    @TestConfiguration
    static class VoteServiceImplTestContextConfiguration {
        @Bean
        public VoteService voteService() {
            return new VoteServiceImpl();
        }
    }

    @Autowired
    VoteService voteService;

    @MockBean
    private VoteRepository voteRepository;

    @BeforeEach
    void setUp() {
        Vote vote1 = new Vote(10,123, 100, 1);
        Vote vote2 = new Vote(11,124, 100, 2);
        List<Vote> votes1 = new ArrayList<>();
        votes1.add(vote1);
        votes1.add(vote2);

        when(voteRepository.save(vote1)).thenReturn(vote1);
        doAnswer(invocation -> {
            votes1.clear();
            return votes1;
        }).when(voteRepository).deleteAll();

        when(voteRepository.findAll()).thenReturn(votes1);
    }

    @Test
    void addVote() {
        Vote vote1 = new Vote(10,123, 100, 1);
        when(voteRepository.save(vote1)).thenReturn(vote1);
        Vote vote = voteService.addVote(vote1);
        assertThat(vote.getVoteId()).isEqualTo(vote1.getVoteId());
    }

    @Test
    void deleteAllVotes() {
        assertThat(voteRepository.findAll().size()).isEqualTo(2);
        voteService.deleteAllVotes();
        assertThat(voteRepository.findAll().size()).isEqualTo(0);
    }
}