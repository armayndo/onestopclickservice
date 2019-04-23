package com.folk.security;

/**
 * Created by Syarif Hidayat on 22/04/2019.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.folk.server.model.User;
import com.folk.server.repository.IUserRepository;


public class AppUserDetailsService implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(AppUserDetailsService.class);
	
	@Autowired
	private IUserRepository userRepository;
	
	@SuppressWarnings("unused")
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("loadUserByUsername from EmployeeUserDetailsService is invoking....");
		User user = userRepository.findByUsername(username);
		
		logger.info("Username: "+user.getUsername()+", Role: "+user.getRole());
		
		if(user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		
		return new AppUserDetails(user);
	}

}