package com.osc.server.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tommy Toban on 07/05/2019.
 */

@Getter
@Setter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PromotedProduct extends BaseModel{
	private String promotedProductName;
	private String promotedProductDescription;
	
	@ManyToMany(
			fetch=FetchType.LAZY
			)
	@JoinTable(
			name="promotedproduct_product",
			joinColumns= {@JoinColumn(name="promotedproduct_id")},
			inverseJoinColumns= {@JoinColumn(name="product_id")}
	)
	private Set<Product> products;
}
