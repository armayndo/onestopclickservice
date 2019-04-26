package com.osc.server.service;

import com.osc.server.model.User;
import com.osc.server.repository.IBaseRepository;
import com.osc.server.repository.IUserRepository;


import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Kerisnarendra on 15/04/2019.
*/
@RestController
@RequestMapping("/api/v1/users")
public class UserService extends BaseService<User> {
	
	@Autowired
	private IUserRepository repository;
	
	private Logger logger = LoggerFactory.getLogger(UserService.class);
	
	/**
	 * Created by Syarif Hidayat on 24/04/2019.
	*/
	/*This service use to register new user using form-data format from frontend WEB UI*/
	@RequestMapping(value="/register", method=RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Map> signin(HttpServletRequest request) {

		Map<Object, Object> model = new HashMap<>();
		User user = new User();
				 
		try {
			String username = request.getParameter("username");
			if(repository.findByUsername(username) == null) {
				user.setUsername(username);
				user.setPassword(new BCryptPasswordEncoder().encode(request.getParameter("password")));
				user.setFirstName(request.getParameter("firstname"));
				user.setLastName(request.getParameter("lastname"));	
				user.setEmail(request.getParameter("email"));
				user.setRole("User");	
				//logger.info("Enabled: "+Boolean.parseBoolean(request.getParameter("enabled")));
				//user.setEnabled(Boolean.parseBoolean(request.getParameter("enabled")));
				user.setEnabled(true);
				repository.save(user);
		        model.put("user", user);
			}else
			{
				model.put("Error", "username is not available, please find the new one");
			}
				                
	    } catch (Exception e) {
	    	model.put("Error", e.getMessage());
	    }
		 
		return ok(model);
	}
	
	/**
	 * Created by Syarif Hidayat on 25/04/2019.
	*/
	/*This service use to update user data from frontend WEB UI*/
	@RequestMapping(value="/user/edit", method=RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map> UpdateUser(@RequestBody User user) {

		Map<Object, Object> model = new HashMap<>();
		logger.info("Id from Client: "+user.getId());
		User userdata = repository.findById(user.getId()).get();
		logger.info("Username: "+ userdata.getUsername());
		logger.info("FirstName: "+ userdata.getFirstName());
		
		if(user.getUsername() != "") {
			userdata.setUsername(user.getUsername());
		}
		
		if(user.getPassword() != "" && user.getPassword() != userdata.getPassword()) {
			userdata.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		}
		
		if(user.getFirstName() != "") {
			userdata.setFirstName(user.getFirstName());
		}
		
		if(user.getLastName() != "") {
			userdata.setLastName(user.getLastName());
		}
		
		if(user.getEmail() != "") {
			userdata.setEmail(user.getEmail());
		}
		
		try {		
			repository.save(userdata);
		    model.put("user", userdata);
					                
	    } catch (Exception e) {
	    	model.put("Error", e.getMessage());
	    }
		 
		return ok(model);
	 }
	
	
}

