package tric.tricproject.Service;

import org.springframework.stereotype.Service;
import tric.tricproject.Security.JwtResponse;
import tric.tricproject.Security.LoginRequest;


/**
 * Service interface
 * containing a method to authenticate user
 *
 * @author Bogdan Mezei
 * @version 1.0, October 2022
 */
@Service
public interface AuthService {
    JwtResponse authenticateUser(LoginRequest loginRequest);
}
