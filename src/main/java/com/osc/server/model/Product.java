package com.osc.server.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tommy Toban on 25/04/2019.
 */

@Getter
@Setter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product extends BaseModel{
	private String name;
	private String description;
	private BigDecimal price;
	private String imageUrl;
	
	@ManyToMany(
			fetch = FetchType.LAZY,
			cascade = {CascadeType.MERGE,CascadeType.PERSIST}
	)
	@JoinTable(
			name="product_category",
			joinColumns={@JoinColumn(name="product_id")},
			inverseJoinColumns= {@JoinColumn(name="category_id")}
    )
	@OnDelete(action=OnDeleteAction.CASCADE)
	@JsonIgnore
	private Set<Category> categories = new HashSet<>();
	
	
	
	
}
