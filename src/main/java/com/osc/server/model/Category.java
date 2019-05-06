package com.osc.server.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tommy Toban on 02/05/2019.
 */

@Getter
@Setter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category extends BaseModel{
	private String categoryName;
	private String categoryDescription;
	
//	@ManyToMany(
//			fetch = FetchType.LAZY,
//			cascade = {CascadeType.MERGE,CascadeType.PERSIST},
//			mappedBy="categories"
//	)
//	@OnDelete(action=OnDeleteAction.CASCADE)
//	@JsonIgnore
//	private Set<Product> products = new HashSet<>();
	
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy="category",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	@JsonIgnore
	private Set<SubCategory> subCategories = new HashSet<>();
	
	
	
	
	
}
