package com.osc.server.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tommy Toban on 25/04/2019.
 */

@Getter
@Setter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@EqualsAndHashCode(callSuper=false)
public class Product extends BaseModel{
	private String name;
	private String description;
	private BigDecimal price;
	private String imageUrl;

//	
//	@ManyToMany(
//			fetch = FetchType.LAZY,
//			cascade = {CascadeType.MERGE,CascadeType.PERSIST}
//	)
//	@JoinTable(
//			name="product_category",
//			joinColumns={@JoinColumn(name="product_id")},
//			inverseJoinColumns= {@JoinColumn(name="category_id")}
//    )
//	@OnDelete(action=OnDeleteAction.CASCADE)
//	@JsonIgnore
//	private Set<Category> categories = new HashSet<>();
	
	
	@ManyToMany(
			fetch = FetchType.LAZY,
			cascade = {CascadeType.MERGE,CascadeType.PERSIST}
	)
	@JoinTable(
			name="product_subcategory",
			joinColumns={@JoinColumn(name="product_id")},
			inverseJoinColumns= {@JoinColumn(name="subcategory_id")}
    )
	@OnDelete(action=OnDeleteAction.CASCADE)
	@JsonIgnore
	private Set<SubCategory> subCategories = new HashSet<>();	
	
	
	@ManyToMany(
			fetch=FetchType.LAZY,
			mappedBy="products")
	@JsonIgnore
	private Set<PromotedProduct> promotedProducts;
	
	
	
	


	@OneToMany(mappedBy = "product")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Set<ProductDetail> productDetails;

	@OneToMany(mappedBy = "product")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Set<ProductReview> productReviews;
	
	
	
}
