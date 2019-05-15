package com.osc.server.service;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.osc.exception.ResourceNotFoundException;
import com.osc.security.AppUserDetails;
import com.osc.server.model.Role;
import com.osc.server.model.User;
import com.osc.server.repository.IUserRepository;


@Service
public class HelperServices {

	@Autowired
	IUserRepository userRepository;
	private Logger logger = LoggerFactory.getLogger(HelperServices.class);
	User user;
	
	@Transactional
	public Set<Role> getRolesByUserId(long id){
		
		logger.info("GetRolesByUserId...");
		User user = userRepository.findById(id).get();
		logger.info("Username: "+user.getUsername()+ " iD:"+user.getId());
		Set<Role> roles = user.getRoles();
		
		if(roles == null) {
			logger.info("Roles is null");
		}
		
		
		return roles;	
	}
}
