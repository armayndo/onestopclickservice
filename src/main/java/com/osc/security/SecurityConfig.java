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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.osc.security.jwt.JwtConfigurer;
import com.osc.security.jwt.JwtTokenFilter;
import com.osc.security.jwt.JwtTokenProvider;
import com.osc.security.oauth2.CustomOAuth2UserService;
import com.osc.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.osc.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.osc.security.oauth2.OAuth2AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
		
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	 		
	@Autowired
	PasswordEncoder passwordEncoder;
	
	/*@Autowired
	JwtTokenFilter customFilter;*/
	

	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;

	@Autowired
	private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

	@Autowired
	private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

	@Autowired
	private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
 
	@Bean
	public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
		return new HttpCookieOAuth2AuthorizationRequestRepository();
	}
		
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
	
	@Bean
	public JwtTokenFilter customFilter() {
		logger.info("set JwtTokenFilter.....");
	    return new JwtTokenFilter(jwtTokenProvider);
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
	        .cors().and()
	        .authorizeRequests()
	        .antMatchers("/auth/signin").permitAll()

	        .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()  
	        .antMatchers("/auth/login").permitAll() //for hal browser login
	        .antMatchers(HttpMethod.GET, "/browser/**").permitAll()
	        .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
//	        .antMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")

	        .anyRequest().authenticated()
	        .and()
	        //.apply(new JwtConfigurer(jwtTokenProvider));
	        .oauth2Login()
            .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                .and()
            .redirectionEndpoint()
                .baseUri("/oauth2/callback/*")
                .and()
            .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
            .successHandler(oAuth2AuthenticationSuccessHandler)
            .failureHandler(oAuth2AuthenticationFailureHandler);
	     	     
	        http.addFilterBefore(customFilter(), UsernamePasswordAuthenticationFilter.class);
	 }
	 
}