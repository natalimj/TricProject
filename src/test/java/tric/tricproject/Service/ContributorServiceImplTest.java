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
import tric.tricproject.Model.Contributor;
import tric.tricproject.Repository.ContributorRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ContributorServiceImpl.class)
class ContributorServiceImplTest {

    @TestConfiguration
    static class ContributorServiceImplTestContextConfiguration {
        @Bean
        public ContributorService contributorService() {
            return new ContributorServiceImpl();
        }
    }

    @Autowired
    ContributorService contributorService;

    @MockBean
    private ContributorRepository contributorRepository;

    @BeforeEach
    void setUp() {
        Contributor contributor1 = new Contributor(123, "John", "He is an actor", "Cast");
        Contributor contributor2 = new Contributor(124, "Anna", "She is a Director", "Cast");
        Contributor contributor3 = new Contributor(125, "Mariah", "She is a Developer", "Dev Team");
        List<Contributor> list = new ArrayList<>();
        list.add(contributor1);
        list.add(contributor2);
        list.add(contributor3);

        when(contributorRepository.findAll()).thenReturn(list);

        List<Contributor> list2 = new ArrayList<>();
        list2.add(contributor1);
        list2.add(contributor2);
        List<Contributor> list3 = new ArrayList<>();
        list3.add(contributor3);
        when(contributorRepository.findAllByType("Cast")).thenReturn(list2);
        when(contributorRepository.findAllByType("Dev Team")).thenReturn(list3);

        doAnswer(invocation -> {
            list.remove(contributor1);
            return list;
        }).when(contributorRepository).deleteById(123L);
    }

    @Test
    void addContributor() {
        Contributor contributor1 = new Contributor(123, "John", "He is an actor", "Cast");
        when(contributorRepository.save(contributor1)).thenReturn(contributor1);
        Contributor saved = contributorService.addContributor(contributor1);

        assertThat(saved).isEqualTo(contributor1);
    }

    @Test
    void getAllContributors() {
        List<Contributor> contributors = contributorService.getAllContributors();

        assertThat(contributors.get(0).getContributorId()).isEqualTo(123);
        assertThat(contributors.get(1).getContributorId()).isEqualTo(124);
        assertThat(contributors.get(2).getContributorId()).isEqualTo(125);
        assertThat(contributors.size()).isEqualTo(3);
    }

    @Test
    void editContributor() {
        Contributor contributor = new Contributor(123, "Johny", "He is an actor", "Cast");
        given(contributorRepository.save(contributor)).willReturn(contributor);
        Contributor saved = contributorService.addContributor(contributor);

        assertThat(saved).isEqualTo(contributor);
    }

    @Test
    void getCast() {
        List<Contributor> contributors = contributorService.getCast();

        assertThat(contributors.get(0).getContributorId()).isEqualTo(123);
        assertThat(contributors.get(1).getContributorId()).isEqualTo(124);
        assertThat(contributors.size()).isEqualTo(2);
    }

    @Test
    void getDevTeam() {
        List<Contributor> contributors = contributorService.getDevTeam();

        assertThat(contributors.get(0).getContributorId()).isEqualTo(125);
        assertThat(contributors.size()).isEqualTo(1);
    }

    @Test
    void deleteContributorById() {
        contributorService.deleteContributorById(123L);
        List<Contributor> contributors = contributorService.getAllContributors();
        assertThat(contributors.get(0).getContributorId()).isEqualTo(124);
        assertThat(contributors.get(1).getContributorId()).isEqualTo(125);
        assertThat(contributors.size()).isEqualTo(2);
    }
}