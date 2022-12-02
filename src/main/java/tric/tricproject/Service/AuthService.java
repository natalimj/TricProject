package tric.tricproject.Service;

import org.springframework.stereotype.Service;
import tric.tricproject.Security.JwtResponse;
import tric.tricproject.Security.LoginRequest;

@Service
public interface AuthService {
    JwtResponse authenticateUser(LoginRequest loginRequest);
}
