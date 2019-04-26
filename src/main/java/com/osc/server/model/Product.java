package com.osc.server.model;

import java.math.BigDecimal;

import javax.persistence.Entity;

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
	
}
