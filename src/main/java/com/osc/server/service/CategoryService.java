package com.osc.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.osc.exception.ResourceNotFoundException;
import com.osc.server.model.Category;
import com.osc.server.repository.ICategoryRepository;

/**
 * Created by Tommy Toban on 02/05/2019.
 */

@RestController
@RequestMapping("/api/v1/category")
public class CategoryService extends BaseService<Category>{
	
	@Autowired
	private ICategoryRepository categoryRepository;
	
//	@PostMapping
//    public Category create(@RequestBody Category category) {
//		
//		Long parentId = category.getParentId();
//		Category parentCategory;
//		if(parentId > 0) {
//			// find parent 
//			parentCategory = categoryRepository.findById(parentId).orElse(null);
//			if(parentCategory != null) {
//				//parentCategory.getSubCategories().add(category);
//				category.setParent(parentCategory);
//				
//			}
//					
//		}
//		
//		
//        return categoryRepository.save(category);
//    }
	
	@PutMapping("/{id}")
    public Category update(@PathVariable long id,@RequestBody Category category) {
		Category categoryUpdate = categoryRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Category", id));
//		Long parentId = category.getParent().getId();
//		Category parentCategory;
//		if(parentId > 0) {
//			// find parent 
//			parentCategory = categoryRepository.findById(parentId).orElse(null);
//			if(parentCategory != null) {
//				//parentCategory.getSubCategories().add(category);
//				category.setParent(parentCategory);
//				
//			}
//					
//		}
//		categoryUpdate.setCategoryName(category.getCategoryName());
//		categoryUpdate.setCategoryDescription(category.getCategoryDescription());
//		
//	
		
		
        return categoryRepository.save(categoryUpdate);
    }
	
//	@PutMapping("/{id}")
//    public Category updateWithParent(@PathVariable long id,@RequestBody Category category) {
//		Category categoryUpdate = categoryRepository.findById(id)
//				.orElseThrow(()->new ResourceNotFoundException("Category", id));
//		
//	
//		
//		Long parentId = category.getParent().getId();
//		Category parentCategory;
//		if(parentId > 0) {
//			// find parent 
//			parentCategory = categoryRepository.findById(parentId).orElse(null);
//			if(parentCategory != null) {
//				//parentCategory.getSubCategories().add(category);
//				category.setParent(parentCategory);
//				
//			}
//					
//		}
//		categoryUpdate.setCategoryName(category.getCategoryName());
//		categoryUpdate.setCategoryDescription(category.getCategoryDescription());
//		
//	
//		
//		
//        return categoryRepository.save(categoryUpdate);
//    }
//	
//	
	
	
}
