package com.osc.server.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osc.exception.ResourceNotFoundException;
import com.osc.server.model.Product;
import com.osc.server.model.Purchase;
import com.osc.server.model.PurchaseDetail;
import com.osc.server.model.User;
import com.osc.server.repository.IProductRepository;
import com.osc.server.repository.IPurchaseDetailRepository;
import com.osc.server.repository.IPurchaseRepository;
import com.osc.server.repository.IUserRepository;

/**
 * Created by Tommy Toban on 13/05/2019.
 */

@RestController
@RequestMapping("/api/v1/purchases")
public class PurchaseService extends BaseService<Purchase>{
	
	@Autowired
	IPurchaseRepository purchaseRepository;
	
	@Autowired
	IProductRepository productRepository;
	
	@Autowired
	IPurchaseDetailRepository purchaseDetailRepository;
	
	@Autowired
	IUserRepository userRepository;
	
	
	
	@PostMapping("/main")
	public Purchase saveWithDetails(@RequestBody Purchase purchase) {
		
		
		

		
		return null;
	}
	
	

	
}
