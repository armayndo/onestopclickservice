package com.osc.server.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tommy Toban on 15/05/2019.
 */

@Getter
@Setter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserAccount extends BaseModel{
	private BigDecimal balance;
	
	@OneToOne(cascade= {CascadeType.MERGE,CascadeType.PERSIST})
	@JoinColumn(name="user_id")
	private User user;
	
	
}
