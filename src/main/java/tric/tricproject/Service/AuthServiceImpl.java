package tric.tricproject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tric.tricproject.Security.JwtResponse;
import tric.tricproject.Security.LoginRequest;
import tric.tricproject.Security.jwt.JwtUtils;
import tric.tricproject.Security.services.UserDetailsImpl;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation class
 * containing a method to authenticate user
 *
 * @version 1.0, October 2022
 */
@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    /**
     * A method to authenticate the admin user and create JWT token
     *
     * @param loginRequest the login request holding username and password
     * @return the jwt response including user details data and JWT
     */
    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        JwtResponse jwtResponse = new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles);

        return jwtResponse;
    }
}
