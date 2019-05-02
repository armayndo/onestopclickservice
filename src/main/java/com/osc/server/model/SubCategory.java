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
public class SubCategory extends BaseModel{
	private String subCategoryName;
	private String subCategoryDescription;
}
