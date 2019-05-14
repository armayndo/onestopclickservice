package com.osc.security;

import java.util.ArrayList;

/**
 * Created by Syarif Hidayat on 22/04/2019.
 */

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.osc.server.model.Role;
import com.osc.server.model.User;


public class AppUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	
	private Logger logger = LoggerFactory.getLogger(AppUserDetails.class);
	
	public AppUserDetails(User user) {
		super();
		this.user = user;
		logger.info("EmployeeUserDetails is invoking....");
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//String role = user.getRole();
		Set<Role> roles = user.getRoles();
		List<GrantedAuthority> roleList = new ArrayList<GrantedAuthority>();
		int count = 0;
		
		if(roles.isEmpty() || roles == null) {
			return Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
		}
		
		for(Role role : roles) {
			count++;
			roleList.add(new SimpleGrantedAuthority(role.getRoleName()));
			logger.info("Role ke-"+count+":"+ role.getRoleName());
		}
		

		logger.info("getAuthorities from EmployeeUserDetails is invoking....");
		//return Collections.singleton(new SimpleGrantedAuthority(role));
		
		return roleList;
	}

	@Override
	public String getPassword() {
		logger.info("getPassword from EmployeeUserDetails is invoking....");
		logger.info("Encoded Password is:"+user.getPassword());
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		logger.info("getUsername from EmployeeUserDetails is invoking....");
		logger.info("Username is: "+user.getUsername());
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return user.isEnabled();
	}

}
