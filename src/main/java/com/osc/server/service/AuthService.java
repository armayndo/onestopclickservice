package com.osc.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osc.common.AuthenticationRequest;
import com.osc.security.jwt.JwtTokenProvider;
import com.osc.server.repository.IUserRepository;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthService {

   /* @Autowired
    AuthenticationManager authenticationManager;*/

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    IUserRepository users;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(AuthService.class);

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody AuthenticationRequest data) {

        try {
            String username = data.getUsername();
            logger.info("Username from client: "+ username);
            logger.info("Pasword from client: "+ data.getPassword());
            //logger.info("Encoded Pasword from client: "+ new BCryptPasswordEncoder().encode(data.getPassword()));
            //authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));


            try {
                authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
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
}
