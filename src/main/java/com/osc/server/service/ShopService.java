package com.osc.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osc.exception.ResourceNotFoundException;
import com.osc.server.model.Product;
import com.osc.server.repository.IProductRepository;

/**
 * Created by Tommy Toban on 10/05/2019.
 */
//@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/v1/shop")
public class ShopService {
	
	@Autowired
	private IProductRepository productRepository;
	
	@GetMapping("/products")
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	@GetMapping("/products/{id}")
	public Product getAllProducts(@PathVariable Long id){
		return productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product", id));
	}
	
	
	
	
}
