package tric.tricproject.Service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import tric.tricproject.Security.LoginRequest;
import tric.tricproject.Security.jwt.JwtUtils;
import tric.tricproject.Security.services.UserDetailsImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthServiceImpl.class)
class AuthServiceImplTest {

    @TestConfiguration
    static class AuthServiceImplTestContextConfiguration {
        @Bean
        public AuthService authService() {
            return new AuthServiceImpl();
        }
    }

    @Autowired
    AuthService authService;

    @MockBean
    AuthenticationManager authenticationManager;
    @MockBean
    JwtUtils jwtUtils;

    @BeforeEach
    void setUp() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new UserDetailsImpl(123L, "username", "password", null),
                "password");

        when(jwtUtils.generateJwtToken(authentication)).thenReturn("123abc");
    }

    @Test
    void authenticateUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        Collection<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ADMIN"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new UserDetailsImpl(123L, "username", "password", roles),
                "password");
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()))).thenReturn(authentication);
        authService.authenticateUser(loginRequest);
    }
}