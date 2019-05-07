package com.osc.server.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Activity extends BaseModel {

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	Token token;
	
	private String activity;
	private Instant time;
	
	public Activity() {
		
	}
	
	public Activity(String activity, Instant time, Token token) {
		this.activity = activity;
		this.time = time;
		this.token = token;
	}
}
