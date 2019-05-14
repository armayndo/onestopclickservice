package com.osc.server.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tommy Toban on 13/05/2019.
 */

@Getter
@Setter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Payment extends BaseModel{
	private Date paymentDate;
	private BigDecimal paymentAmount;
	
	@OneToOne(cascade= {CascadeType.MERGE,CascadeType.PERSIST})
	@JoinColumn(name="purchase_id")
	private Purchase purchase;
	
	private Integer paymentMethod; // 1 = user balance , 2 = paypal
	
}
