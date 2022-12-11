package tric.tricproject.Security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tric.tricproject.Model.Admin;
import tric.tricproject.Repository.AdminRepository;

/**
 *
 * @author bezkoder
 * September,2022
 * see https://www.bezkoder.com/spring-boot-jwt-authentication/
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired AdminRepository adminRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin admin = adminRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(admin);
	}

}