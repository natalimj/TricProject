package tric.tricproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import tric.tricproject.Controller.AuthController;
import tric.tricproject.Controller.UserController;
import tric.tricproject.Repository.AdminRepository;
import tric.tricproject.Repository.RoleRepository;
import tric.tricproject.Security.jwt.JwtUtils;
import tric.tricproject.Security.request.LoginRequest;
import tric.tricproject.Security.request.SignupRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    AdminRepository adminRepository;

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    PasswordEncoder encoder;

    @MockBean
    JwtUtils jwtUtils;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAuthenticateUser() throws Exception {

        /*
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("user");
        loginRequest.setPassword("password");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        loginRequest.setUsername("user");
        loginRequest.setPassword("password");
        mockMvc.perform(post("/api/auth/signin",loginRequest).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andDo(print());


         */
    }

    @Test
    void shouldRegisterUser() throws Exception {

       /* SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("username");
        signupRequest.setPassword("password");
        mockMvc.perform(post("/api/auth/signup",signupRequest).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(status().isOk())
                .andDo(print());

        */
    }
}