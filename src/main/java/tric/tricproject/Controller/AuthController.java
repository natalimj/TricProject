package tric.tricproject.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tric.tricproject.Security.LoginRequest;
import tric.tricproject.Security.JwtResponse;
import tric.tricproject.Service.AuthService;

/**
 * Controller class for admin authentication
 *
 * @author Bogdan Mezei
 * @version 1.0, October 2022
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/authApi")
public class AuthController {
	@Autowired
	AuthService authService;

	/**
	 * Controller method to authenticate user
	 *
	 * @param loginRequest {@link LoginRequest} object
	 * @return the response entity with HTTP Status and {@link JwtResponse}
	 */
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		try {
			JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
			return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}