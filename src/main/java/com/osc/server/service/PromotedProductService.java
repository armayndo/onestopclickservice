package com.osc.server.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osc.exception.ResourceNotFoundException;
import com.osc.server.model.Product;
import com.osc.server.model.PromotedProduct;
import com.osc.server.repository.IProductRepository;
import com.osc.server.repository.IPromotedProductRepository;

/**
 * Created by Tommy Toban on 07/05/2019.
 */

@RestController
@RequestMapping("/api/v1/promotedproduct")
public class PromotedProductService extends BaseService<PromotedProduct>{
	
	@Autowired
	IPromotedProductRepository promotedProductRepository;
	
	@Autowired
	IProductRepository productRepository;
	
	
	
	@PutMapping("/{id}/add-products")
	public PromotedProduct addMultiProducts(@PathVariable Long id,@RequestBody PromotedProduct promotedProduct){
		PromotedProduct findPromotedProduct = promotedProductRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Promoted Product", id));
		promotedProduct.getProducts().stream().forEach(product->findPromotedProduct.getProducts().add(product));
		
		return promotedProductRepository.save(findPromotedProduct);
	}
	
	@DeleteMapping("/{id}/remove-product/{productId}")
	public PromotedProduct removeProduct(@PathVariable Long id,@PathVariable Long productId) {
		PromotedProduct promotedProduct = promotedProductRepository.findById(id).orElseThrow(
				()->new ResourceNotFoundException("Promoted Product",id));
//		Product product = productRepository.findById(productId).orElseThrow(
//				()->new ResourceNotFoundException("Product", productId));
		
		Product productFind = promotedProduct.getProducts().stream()
		.filter(product->productId.equals(product.getId())).findFirst().orElseThrow(()->new ResourceNotFoundException("Product",productId));
		promotedProduct.getProducts().remove(productFind);
		return promotedProductRepository.save(promotedProduct);
	}
	
	
	
	@DeleteMapping("/{id}/remove-product")
	public PromotedProduct removeMultiProduct(@PathVariable Long id,@RequestBody PromotedProduct promotedProduct) {
		PromotedProduct findPromotedProduct = promotedProductRepository.findById(id).orElseThrow(
				()->new ResourceNotFoundException("Promoted Product",id));

//		promotedProduct.getProducts().stream()
//		.map( product->findPromotedProduct.getProducts().remove(product));
//		
		findPromotedProduct.getProducts().removeIf(product->
		promotedProduct.getProducts().stream().filter(
				p->p.getId()==product.getId()
				).findFirst().orElse(null)!= null
		);
		

		return promotedProductRepository.save(findPromotedProduct);
	}
	

}
