package com.osc.server.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Purchase extends BaseModel{
	private String purchaseNo;
	private Date purchaseDate;
	private BigDecimal purchasetotal;
	private int purchaseQuantity;
	
	@OneToMany(fetch=FetchType.LAZY,
			cascade= {CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(name="purchase_id",nullable=false)
	private Set<PurchaseDetail> purchaseDetails = new HashSet<>();
	
	@OneToOne(mappedBy="purchase")
	@JsonIgnore
	private Payment payment;
	
	@OneToOne(cascade= {CascadeType.MERGE,CascadeType.PERSIST})
	@JoinColumn(name="customer_id")
	private User customer;
	
	
	
}
