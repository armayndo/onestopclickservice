package com.osc.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.osc.exception.ResourceNotFoundException;
import com.osc.server.model.Category;
import com.osc.server.model.SubCategory;
import com.osc.server.repository.ICategoryRepository;
import com.osc.server.repository.ISubCategoryRepository;

/**
 * Created by Tommy Toban on 02/05/2019.
 */

@RestController
@RequestMapping("/api/v1/subcategory")
public class SubCategoryService extends BaseService<SubCategory>{
	
//	@Autowired
//	ISubCategoryRepository subCategoryRepository;
//	
//	@Autowired
//	ICategoryRepository categoryRepository;
	
// @PostMapping()
//  public SubCategory create(@RequestBody SubCategory subCategory) {
//	 Category categorySubmit = subCategory.getCategory();
//	 Long subCategoryId = subCategory.getId();
////	 if(subCategory.getId() > 0) {
////		 subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(
////				 ()->new ResourceNotFoundException("Sub Category",subCategoryId));
////	 }
//	
//	 final Long categoryId;
//	 if(categorySubmit != null) {
//		 System.out.println("id category baru: "+subCategory.getCategory().getId());
//		 categoryId = categorySubmit.getId();
//	 }else {
//		 categoryId = 0L;
//	 }
//	 
//	 if(categoryId > 0) {
//		 Category category = categoryRepository.findById(categoryId).orElseThrow(
//				 ()->new ResourceNotFoundException("Category",categoryId));
//		 subCategory.setCategory(category);
//	 }
//	 
//	 
//		
//		
//      return subCategoryRepository.save(subCategory);
//      
//
//  }
}
