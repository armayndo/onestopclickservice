package com.osc.server.payload;

import org.springframework.beans.factory.annotation.Autowired;

import com.osc.server.repository.IUserRepository;

public class UserPayload {
	
	@Autowired
	private IUserRepository userRepository;
	
	boolean status;
	Object  data;

}
