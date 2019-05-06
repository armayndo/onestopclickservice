package com.osc.server.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osc.exception.ResourceNotFoundException;
import com.osc.server.model.Category;
import com.osc.server.model.Product;
import com.osc.server.model.SubCategory;
import com.osc.server.repository.ICategoryRepository;
import com.osc.server.repository.IProductRepository;
import com.osc.server.repository.ISubCategoryRepository;

/**
 * Created by Tommy Toban on 25/04/2019.
 */

@RestController
@RequestMapping("/api/v1/product")
public class ProductService extends BaseService<Product> {
	
	@Autowired
	private IProductRepository productRepository;
	
//	@Autowired
//	private ICategoryRepository categoryRepository;
	
	@Autowired
	private ISubCategoryRepository subCategoryRepository;
	
//	@GetMapping("/{productId}/categories")
//	public Set<Category> getCategories(@PathVariable Long productId){
//		// return product by id and it's categories, otherwise throws exception
//		return productRepository.findById(productId).map((product)->{
//			return product.getCategories();
//		}).orElseThrow(()->new ResourceNotFoundException("Product",productId));
//	}
//	
//	@PostMapping("/{productId}/categories/{categoryId}")
//	public Set<Category> addCategory(@PathVariable Long productId,@PathVariable Long categoryId){
//		// find persisted category
//		Category category = categoryRepository.findById(categoryId).orElseThrow(
//				()->new ResourceNotFoundException("Category", categoryId));
//		
//		return productRepository.findById(productId).map((product)->{
//			product.getCategories().add(category);
//			return productRepository.save(product).getCategories();
//		}).orElseThrow(()->new ResourceNotFoundException("Product",productId));
//		
//	}
//	
//	@DeleteMapping("/{productId}/categories/{categoryId}")
//	public Set<Category> removeCategory(@PathVariable Long productId,@PathVariable Long categoryId){
//		// find persisted category
//		Category category = categoryRepository.findById(categoryId).orElseThrow(
//				()->new ResourceNotFoundException("Category", categoryId));
//		
//		return productRepository.findById(productId).map((product)->{
//			product.getCategories().remove(category);
//			return productRepository.save(product).getCategories();
//		}).orElseThrow(()->new ResourceNotFoundException("Product", productId));
//	}
	
	
	@GetMapping("/{productId}/subcategories")
	public Set<SubCategory> getSubCategories(@PathVariable Long productId){
		// return product by id and it's sub categories, otherwise throws exception
		return productRepository.findById(productId).map((product)->{
			return product.getSubCategories();
		}).orElseThrow(()->new ResourceNotFoundException("Product",productId));
	}
	
	@PostMapping("/{productId}/subcategories/{subCategoryId}")
	public Set<SubCategory> addSubCategory(@PathVariable Long productId,@PathVariable Long subCategoryId){
		// find persisted sub category
		SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(
				()->new ResourceNotFoundException("SubCategory", subCategoryId));
		
		return productRepository.findById(productId).map((product)->{
			product.getSubCategories().add(subCategory);
			return productRepository.save(product).getSubCategories();
		}).orElseThrow(()->new ResourceNotFoundException("Product",productId));
		
	}
	
	@DeleteMapping("/{productId}/subcategories/{subCategoryId}")
	public Set<SubCategory> removeSubCategory(@PathVariable Long productId,@PathVariable Long subCategoryId){
		// find persisted category
		SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(
				()->new ResourceNotFoundException("SubCategory", subCategoryId));
		
		return productRepository.findById(productId).map((product)->{
			product.getSubCategories().remove(subCategory);
			return productRepository.save(product).getSubCategories();
		}).orElseThrow(()->new ResourceNotFoundException("Product", productId));
	}
}
