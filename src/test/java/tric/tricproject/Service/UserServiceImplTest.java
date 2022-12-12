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
import tric.tricproject.Model.User;
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
@WebMvcTest(UserServiceImpl.class)
class UserServiceImplTest {

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    UserService userService;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user1 = new User(123,"John");
        User user2 = new User(124, "Maria", "/this.jpg");
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        when(userRepository.save(user1)).thenReturn(user1);
        when(userRepository.findAll()).thenReturn(users);

        doAnswer(invocation -> {
            users.clear();
            return users;
        }).when(userRepository).deleteAll();
    }

    @Test
    void addUser() {
        User user1 = new User(123,"John");
        when(userRepository.save(user1)).thenReturn(user1);
        User user = userService.addUser(user1);
        assertThat(user.getUserId()).isEqualTo(user1.getUserId());
    }

    @Test
    void getAllUsers() {
        List<User> users = userService.getAllUsers();
        assertThat(users.size()).isEqualTo(2);
        assertThat(users.get(0).getUserId()).isEqualTo(123);
        assertThat(users.get(1).getUserId()).isEqualTo(124);
    }

    @Test
    void deleteAllUsers() {
        List<User> users = userService.getAllUsers();
        assertThat(users.size()).isEqualTo(2);
        userService.deleteAllUsers();
        users = userService.getAllUsers();
        assertThat(users.size()).isEqualTo(0);
    }
}