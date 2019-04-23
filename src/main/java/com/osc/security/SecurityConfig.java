package com.osc.security;

/**
 * Created by Syarif Hidayat on 22/04/2019.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.osc.security.jwt.JwtConfigurer;
import com.osc.security.jwt.JwtTokenProvider;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	
	/*@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		
		logger.info("DaoAuthentication process.....");
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		
		return provider;
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
		logger.info("set BCrypt Password Encoder.....");
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	public UserDetailsService userDetailsService() {
		logger.info("set EmployeeUserDetailsService.....");
	    return new AppUserDetailsService();
	}*/
	
	/*private UserDetailsService userDetailsService;*/
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	 
	
		
	@Autowired
	PasswordEncoder passwordEncoder;
		
	@Bean
	public AuthenticationProvider authenticationProvider() {
			
		logger.info("Authentication process.....");
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(new AppUserDetailsService());
		provider.setPasswordEncoder(passwordEncoder);
			
		return provider;
	}
		
	@Bean
	public PasswordEncoder passwordEncoder() {
		logger.info("set BCrypt Password Encoder.....");
	    return new BCryptPasswordEncoder();
	}
		
	/*@Bean
	public UserDetailsService userDetailsService() {
		logger.info("set EmployeeUserDetailsService.....");
		return new AppUserDetailsService();
	}*/

	/* @Bean
	 @Override
	 public AuthenticationManager authenticationManagerBean() throws Exception {
		
		 return super.authenticationManagerBean();
	 }*/

	 @Override
	 protected void configure(HttpSecurity http) throws Exception {
	     http
	     
	     	.httpBasic().disable()
	        .csrf().disable()
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()
	        .authorizeRequests()
	        .antMatchers("/auth/signin").permitAll()
	        //.antMatchers(HttpMethod.GET, "/**").permitAll()
	        .antMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
	        //.antMatchers(HttpMethod.GET, "/v1/**").permitAll()
	        .anyRequest().authenticated()
	        .and()
	        .apply(new JwtConfigurer(jwtTokenProvider));
	 }
	 
}