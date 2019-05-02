package com.osc.server.model;

import javax.persistence.Entity;

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
	
}
