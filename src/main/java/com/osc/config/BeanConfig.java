package com.osc.config;

/**
 * Created by Syarif Hidayat on 22/04/2019.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.osc.security.AppUserDetailsService;

@Configuration
public class BeanConfig {

	private Logger logger = LoggerFactory.getLogger(BeanConfig.class);
	
	@Bean
	public UserDetailsService userDetailsService() {
		logger.info("Created UserDetailsService.....");
		return new AppUserDetailsService();
	}
}
