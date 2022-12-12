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
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import tric.tricproject.Model.Contributor;
import tric.tricproject.Model.PlayInfo;
import tric.tricproject.Repository.PlayInfoRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(PlayInfoServiceImpl.class)
class PlayInfoServiceImplTest {

    @TestConfiguration
    static class PlayInfoServiceImplTestContextConfiguration {
        @Bean
        public PlayInfoService playInfoService() {
            return new PlayInfoServiceImpl();
        }
    }

    @Autowired
    PlayInfoService playInfoService;

    @MockBean
    private PlayInfoRepository playInfoRepository;

    @BeforeEach
    void setUp() {
        Contributor contributor1 = new Contributor(123, "John", "He is an actor", "Cast");
        Contributor contributor2 = new Contributor(124, "Anna", "She is a Director", "Cast");
        Contributor contributor3 = new Contributor(125, "Mariah", "She is a Developer", "Dev Team");
        List<Contributor> list = new ArrayList<>();
        list.add(contributor1);
        list.add(contributor2);
        list.add(contributor3);
        PlayInfo playInfo = new PlayInfo(1, "This is about the play", "Showing result...", false, list);

        when(playInfoRepository.findById(1L)).thenReturn(java.util.Optional.of(playInfo));
        when(playInfoRepository.save(playInfo)).thenReturn(playInfo);
    }

    @Test
    void editPlayInfo() {
        Contributor contributor1 = new Contributor(123, "John", "He is an actor", "Cast");
        Contributor contributor2 = new Contributor(124, "Anna", "She is a Director", "Cast");
        Contributor contributor3 = new Contributor(125, "Mariah", "She is a Developer", "Dev Team");
        List<Contributor> list = new ArrayList<>();
        list.add(contributor1);
        list.add(contributor2);
        list.add(contributor3);
        PlayInfo playInfo = new PlayInfo(1, "This is about the play", "Showing result...", false, list);

        when(playInfoRepository.save(playInfo)).thenReturn(playInfo);
        PlayInfo result = playInfoService.editPlayInfo(playInfo);

        assertThat(result).isEqualTo(playInfo);
    }

    @Test
    void getPlayInfo() {
        Contributor contributor1 = new Contributor(123, "John", "He is an actor", "Cast");
        Contributor contributor2 = new Contributor(124, "Anna", "She is a Director", "Cast");
        Contributor contributor3 = new Contributor(125, "Mariah", "She is a Developer", "Dev Team");
        List<Contributor> list = new ArrayList<>();
        list.add(contributor1);
        list.add(contributor2);
        list.add(contributor3);
        PlayInfo playInfo = new PlayInfo(1, "This is about the play", "Showing result...", false, list);

        PlayInfo result = playInfoService.getPlayInfo();
        assertThat(result.getPlayInfoId()).isEqualTo(playInfo.getPlayInfoId());
        assertThat(result.getPlayInfoText()).isEqualTo(playInfo.getPlayInfoText());
        assertThat(result.getFinalResultText()).isEqualTo(playInfo.getFinalResultText());
        assertThat(result.isActive()).isEqualTo(playInfo.isActive());
    }

    @Test
    void setAppStatus() {
        PlayInfo result = playInfoService.setAppStatus(true);
        assertThat(result.isActive()).isEqualTo(true);
    }

    @Test
    void getStatus() {
        assertThat(playInfoService.getStatus()).isEqualTo(false);
    }
}