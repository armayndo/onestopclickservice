package com.osc.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.osc.server.exception.ResourceNotFoundException;
import com.osc.server.model.Activity;
import com.osc.server.model.Token;
import com.osc.server.model.User;
import com.osc.server.payload.AuthResponse;
import com.osc.server.payload.LoginRequest;
import com.osc.common.AuthenticationRequest;
import com.osc.security.CurrentUser;
import com.osc.security.UserPrincipal;
import com.osc.security.jwt.JwtTokenProvider;
import com.osc.server.repository.IActivityRepository;
import com.osc.server.repository.ITokenRepository;
import com.osc.server.repository.IUserRepository;

import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthService extends CrossOriginService{

   /* @Autowired
    AuthenticationManager authenticationManager;*/

	@Autowired
	ITokenRepository tokenRepository;
	
	@Autowired
	IActivityRepository activityRepository;
	
    @Autowired
    AuthenticationProvider authenticationProvider;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    IUserRepository users;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(AuthService.class);

    @RequestMapping(value="/signin", method=RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity signin(HttpServletRequest request) {

    	Map<Object, Object> model = new HashMap<>();
    	
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        logger.info("Username from client: "+ username);
		logger.info("Pasword from client: "+ password);
        
        if(username != null && password != null) {
        	try {
    			logger.info("Authenticate User ");
    		    authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username, request.getParameter("password")));
    		}catch (DisabledException e) {
    			model.put("Error", "User is disabled");
    			return ok(model);
    		    //throw new DisabledException("User is disabled!", e);
    		    
    		}catch (BadCredentialsException e) {
    			model.put("Error", "Bad credentials!");
    			return ok(model);
    		    //throw new BadCredentialsException("Bad credentials!", e);
    		}catch(InternalAuthenticationServiceException e) {
    			model.put("Error", "Username not found");
    			return ok(model);
    			//throw new InternalAuthenticationServiceException("Username not found!", e);
    		}
    		catch(Exception e) {
    		    logger.info("Error Login: "+e.getMessage(), e);
    		    model.put("Error", "Internal Server Error");
    		    return ok(model);
    		    //throw new BadCredentialsException("Bad credentials!", e);
    		}


    		String token = jwtTokenProvider.createToken(username, this.users.findByUsername(username).getRole());
    		logger.info("Generated Token: "+ token);
    		
    		/*
    		 * Save generated token with its username to databases if the username doesn't exist yet
    		 * or just update the token if its username is exist
    		 * */
    		Token tokenData = tokenRepository.findByUsername(username);
    		
    		if(tokenData == null) {
    			tokenRepository.save(new Token(username, token, Instant.now()));
    		}else {
    			tokenData.setToken(token);
    			tokenData.setTime(Instant.now());
    			tokenRepository.save(tokenData);
    		}
    		
    		model.put("username", username);
    		model.put("token", token);
        }
           
		return ok(model);
    }
    
    @RequestMapping(value="/logins", method=RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity login(HttpServletRequest request) {

        try {
            String username = request.getParameter("username");
            logger.info("Username from client: "+ username);
            logger.info("Pasword from client: "+ request.getParameter("password"));
            

            try {
                authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username, request.getParameter("password")));
            } catch (DisabledException e) {
                throw new DisabledException("User is disabled!", e);
            } catch (BadCredentialsException e) {
                throw new BadCredentialsException("Bad credentials!", e);
            }catch (Exception e) {
                logger.info("Signin Failed"+ e.getMessage());
            }


            String token = jwtTokenProvider.createToken(username, this.users.findByUsername(username).getRole());
            logger.info("Generated Token: "+ token);

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);

            return ok(model);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
    
    /*
     * Get all existing login data
     * **/
    @GetMapping("/token/list")
    public ResponseEntity getTokenList()
    {
    	Map<Object, Object> model = new HashMap<>();
    	try {
    		List<Token> tokenList = tokenRepository.findAll();
    		
    		for(Token token : tokenList) {
    			logger.info("Username: "+ token.getUsername()+ " Token:"+token.getToken());
    		}
    		
    		
       	 	
            model.put("tokens", tokenList);
    	}catch(Exception e) {
    		logger.info(e.getMessage());
    		return new ResponseEntity("Error", HttpStatus.NOT_FOUND);
    	}
    	
		return ok(model);	
    }
    
    @GetMapping("/activity/list/{id}")
    public ResponseEntity getActivityListById(@PathVariable("id") int id) {
		
    	Map<Object, Object> map = new HashMap<>();
    	
    	try {
    		Token token = tokenRepository.findById(Long.valueOf(id)).get();
    		
    		if(token != null) {
    			List<Activity> activityList = token.getActivity();
        		map.put("activities", activityList);
    		}else {
    			map.put("Error", "Data is not found");
    		}
    		
    	}catch(BadRequest e) {
    		return new ResponseEntity("Error", HttpStatus.BAD_REQUEST);
    	}
    	
    	return ok(map);
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
               
        try {
            String username = loginRequest.getEmail();
            logger.info("Username from client: "+ username);
            logger.info("Pasword from client: "+ loginRequest.getPassword());
            

            try {
                authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username, loginRequest.getPassword()));
            } catch (DisabledException e) {
                throw new DisabledException("User is disabled!", e);
            } catch (BadCredentialsException e) {
                throw new BadCredentialsException("Bad credentials!", e);
            }catch (Exception e) {
                logger.info("Signin Failed"+ e.getMessage());
            }


            String token = jwtTokenProvider.createToken(username, this.users.findByUsername(username).getRole());
            logger.info("Generated Token: "+ token);

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ResponseEntity.ok(new AuthResponse(token));
            //return ok(model);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
    
    @GetMapping("/user/me")
    public User getCurrentUser() {
    	
    	String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		
    	User user = users.findByUsername(username);
    	logger.info("User ID: "+user.getId());
    	logger.info("Username: "+user.getUsername());
    	logger.info("Email: "+user.getEmail());
    	
    	return user;
    }
}
