package com.osc.security;

/**
 * Created by Syarif Hidayat on 22/04/2019.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.osc.server.model.User;
import com.osc.server.repository.IUserRepository;


public class AppUserDetailsService implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(AppUserDetailsService.class);
	
	@Autowired
	private IUserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("loadUserByUsername from EmployeeUserDetailsService is invoking....");
		logger.info("loadUserByUsername from EmployeeUserDetailsService is invoking....");
		User user = userRepository.findByUsername(username);
		
		if(user == null) {
			logger.info("User is null from loadUserByUsername");
		}
		
		logger.info("Username: "+user.getUsername()+", Role: "+user.getRole());
		
		return new AppUserDetails(user);
	}

}