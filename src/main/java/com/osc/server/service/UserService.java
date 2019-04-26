package com.osc.server.service;

import com.osc.exception.ResourceNotFoundException;
import com.osc.server.model.Role;
import com.osc.server.model.User;
import com.osc.server.repository.IRoleRepository;
import com.osc.server.repository.IUserRepository;


import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Kerisnarendra on 15/04/2019.
*/
//@CrossOrigin(origins="http://localhost:3000") // added by Tommy 25/04/2019
@RestController
@RequestMapping("/api/v1/users")
public class UserService extends BaseService<User> {
	
	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IRoleRepository roleRepository;

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
			if(userRepository.findByUsername(username) == null) {
				user.setUsername(username);
				user.setPassword(new BCryptPasswordEncoder().encode(request.getParameter("password")));
				user.setFirstName(request.getParameter("firstname"));
				user.setLastName(request.getParameter("lastname"));	
				user.setEmail(request.getParameter("email"));
				user.setRole("User");	
				//logger.info("Enabled: "+Boolean.parseBoolean(request.getParameter("enabled")));
				//user.setEnabled(Boolean.parseBoolean(request.getParameter("enabled")));
				user.setEnabled(true);
				userRepository.save(user);
				//user.setRole(request.getParameter("role"));	
				//logger.info("Enabled: "+Boolean.parseBoolean(request.getParameter("enabled")));
				//user.setEnabled(Boolean.parseBoolean(request.getParameter("enabled")));
				//userRepository.save(user);
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
		User userdata = userRepository.findById(user.getId()).get();
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
			userRepository.save(userdata);
		    model.put("user", userdata);
					                
	    } catch (Exception e) {
	    	model.put("Error", e.getMessage());
	    }
		 
		return ok(model);
	 }


	@GetMapping("/{userId}/roles")
	public Set<Role> getRoles(@PathVariable Long userId){
		// Finds user by id and returns it's recorded roles, otherwise throws exception
		return this.userRepository.findById(userId).map((user) -> {
			return user.getRoles();
		}).orElseThrow(() -> new ResourceNotFoundException("User", userId));
	}

	@PostMapping("/{id}/roles/{roleId}") // Path variable names must match with method's signature variables.
	public Set<Role> addRole(@PathVariable Long id, @PathVariable Long roleId){
		// Finds a persisted role
		Role role = this.roleRepository.findById(roleId).orElseThrow(
				() -> new ResourceNotFoundException("Role", roleId)
		);

		// Finds a user and adds the given role to the user's set.
		return this.userRepository.findById(id).map((user) -> {
			user.getRoles().add(role);
			return this.userRepository.save(user).getRoles();
		}).orElseThrow(() -> new ResourceNotFoundException("User", id));
	}

}

