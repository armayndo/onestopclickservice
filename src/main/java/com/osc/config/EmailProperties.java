package com.osc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "user")
public class EmailProperties {
	
	private String email;
	private String password;

}
