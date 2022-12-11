package tric.tricproject.Security;

import javax.validation.constraints.NotBlank;

/**
 * @author bezkoder
 * September,2022
 * see https://www.bezkoder.com/spring-boot-jwt-authentication/
 */
public class LoginRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
