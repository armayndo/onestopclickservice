package com.osc.server.service;

import java.util.Set;
import java.util.UUID;

import com.osc.common.Common;
import com.osc.exception.RequestValidationException;
import com.osc.server.model.*;
import com.osc.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import com.osc.exception.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Created by Tommy Toban on 25/04/2019.
 */

@RestController
@RequestMapping("/api/v1/products")
public class ProductService extends BaseService<Product> {
	@Autowired
	private Common common;

	@Autowired
	private IProductRepository productRepository;

    @Autowired
    private IProductDetailRepository productDetailRepository;

    @Autowired
    private FileStorageService fileStorageService;

	@Autowired
	private IProductReviewRepository productReviewRepository;

	@Autowired
	private IUserRepository userRepository;
	
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

    // Product Details
    @GetMapping("/{productId}/details")
    public Set<ProductDetail> getDetails(@PathVariable Long productId){
        // Finds product by id and returns it's recorded details, otherwise throws exception
        return this.productRepository.findById(productId).map((product) -> {
			Set<ProductDetail> productDetails = product.getProductDetails();
			productDetails.forEach(productDetail -> {
				String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/downloadFile/")
						.path(productDetail.getProductDetailFileName())
						.toUriString();
				productDetail.setProductDetailFileName(fileDownloadUri);
			});
            return productDetails;
        }).orElseThrow(() -> new ResourceNotFoundException("Product", productId));
    }

    // Product Details
    @PostMapping("/{productId}/details")
    public ProductDetail addDetails(@PathVariable Long productId, @RequestParam("productDetailFileName") MultipartFile file, @RequestParam("productDetailType") Boolean productDetailType){
        // Finds product by id and returns it's recorded details, otherwise throws exception
        Product product = this.productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product", productId)
        );

        String prefix = "PRODUCT-" + String.valueOf(product.getId()) + "-" + UUID.randomUUID().toString() + "-";
        String fileName = fileStorageService.storeFile(file, prefix);

        ProductDetail productDetail = new ProductDetail();
        productDetail.setProduct(product);
        productDetail.setProductDetailFileName(fileName);
        productDetail.setProductDetailType(productDetailType);

        return productDetailRepository.save(productDetail);
    }

	// Product Details
	@DeleteMapping("/{productId}/details/{productDetailId}")
	public void removeDetails(@PathVariable Long productId, @PathVariable Long productDetailId){
		// Finds product by id and returns it's recorded details, otherwise throws exception
		Product product = this.productRepository.findById(productId).orElseThrow(
				() -> new ResourceNotFoundException("Product", productId)
		);

		ProductDetail productDetail = this.productDetailRepository.findById(productDetailId).orElseThrow(
				() -> new ResourceNotFoundException("Product Detail", productDetailId)
		);

		productDetailRepository.delete(productDetail);
	}

	// Product Reviews
	@GetMapping("/{productId}/reviews")
	public Set<ProductReview> getReviews(@PathVariable Long productId){
		// Finds product by id and returns it's recorded reviews, otherwise throws exception
		return this.productRepository.findById(productId).map((product) -> {
			return product.getProductReviews();
		}).orElseThrow(() -> new ResourceNotFoundException("Product", productId));
	}

	// Product Reviews
	@PostMapping("/{productId}/reviews")
	public ProductReview addReviews(@PathVariable Long productId, @RequestBody ProductReview productReview){
		if (productReview.getProductReviewRate() > Common.MAX_PRODUCT_RATE || productReview.getProductReviewRate() < Common.MIN_PRODUCT_RATE) {
			throw new RequestValidationException("Product review rate must be between " + Common.MIN_PRODUCT_RATE + " and " + Common.MAX_PRODUCT_RATE);
		}

		// Finds product by id and returns it's recorded reviews, otherwise throws exception
		Product product = this.productRepository.findById(productId).orElseThrow(
				() -> new ResourceNotFoundException("Product", productId)
		);

		// Finds user by id and returns it's recorded reviews, otherwise throws exception
		User user = this.userRepository.findById(productReview.getUser().getId()).orElseThrow(
				() -> new ResourceNotFoundException("User", productReview.getUser().getId())
		);

		ProductReview review = new ProductReview();
		review.setProduct(product);
		review.setUser(user);
		review.setProductReviewComment(productReview.getProductReviewComment());
		review.setProductReviewRate(productReview.getProductReviewRate());

		return productReviewRepository.save(review);
	}

	// Product Reviews
	@PutMapping("/{productId}/reviews/{productReviewId}")
	public void updateReviews(@PathVariable Long productId, @PathVariable Long productReviewId, @RequestBody ProductReview productReview){
		// Finds product by id and returns it's recorded reviews, otherwise throws exception
		Product product = this.productRepository.findById(productId).orElseThrow(
				() -> new ResourceNotFoundException("Product", productId)
		);

		ProductReview review = this.productReviewRepository.findById(productReviewId).orElseThrow(
				() -> new ResourceNotFoundException("Product Review", productReviewId)
		);

		if (productReview.getProductReviewRate() > Common.MAX_PRODUCT_RATE || productReview.getProductReviewRate() < Common.MIN_PRODUCT_RATE) {
			throw new RequestValidationException("Product review rate must be between " + Common.MIN_PRODUCT_RATE + " and " + Common.MAX_PRODUCT_RATE);
		}

		review.setProductReviewComment(productReview.getProductReviewComment());
		review.setProductReviewRate(productReview.getProductReviewRate());

		productReviewRepository.save(review);
	}

	// Product Reviews
	@DeleteMapping("/{productId}/reviews/{productReviewId}")
	public void removeReviews(@PathVariable Long productId, @PathVariable Long productReviewId){
		// Finds product by id and returns it's recorded reviews, otherwise throws exception
		Product product = this.productRepository.findById(productId).orElseThrow(
				() -> new ResourceNotFoundException("Product", productId)
		);

		ProductReview productReview = this.productReviewRepository.findById(productReviewId).orElseThrow(
				() -> new ResourceNotFoundException("Product Review", productReviewId)
		);

		productReviewRepository.delete(productReview);
	}
	

}
