package com.osc.server.model;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Token extends BaseModel{
	
	private String token;
	private String username;
	Instant time;

	public Token() {
		
	}
	
	public Token(String username, String token, Instant time) {
		this.username = username;
		this.token = token;
		this.time = time;
	}
	
}
