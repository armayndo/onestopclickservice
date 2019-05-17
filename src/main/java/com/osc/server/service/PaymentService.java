package com.osc.server.service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osc.exception.ResourceNotFoundException;
import com.osc.server.model.Payment;
import com.osc.server.model.Product;
import com.osc.server.model.Purchase;
import com.osc.server.model.PurchaseDetail;
import com.osc.server.model.User;
import com.osc.server.model.UserAccount;
import com.osc.server.repository.IPaymentRepository;
import com.osc.server.repository.IProductRepository;
import com.osc.server.repository.IPurchaseDetailRepository;
import com.osc.server.repository.IPurchaseRepository;
import com.osc.server.repository.IUserAccountRepository;
import com.osc.server.repository.IUserRepository;

/**
 * Created by Tommy Toban on 13/05/2019.
 */

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentService extends BaseService<Payment>{
	
	@Autowired
	IPurchaseRepository purchaseRepository;
	
	@Autowired
	IPaymentRepository paymentRepository;
	
	@Autowired
	IProductRepository productRepository;
	
	@Autowired
	IPurchaseDetailRepository purchaseDetailRepository;
	
	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	IUserAccountRepository userAccountRepository;
	
	@PostMapping("/main")
	@Transactional
	public Payment doPayment(@RequestBody Payment payment) {
		
		Purchase purchase = payment.getPurchase();
		// check customer as user
//				User customer = userRepository.findById(purchase.getCustomer().getId())
//						.orElseThrow(()->new ResourceNotFoundException("User",purchase.getCustomer().getId()));
//				
		User customer = Optional.of(userRepository.findByUsername(purchase.getCustomer().getUsername()))
				.orElseThrow(()->new ResourceNotFoundException("User",purchase.getCustomer().getId()));
		
		
				purchase.setCustomer(customer);
				
				
				// check product exist
				purchase.setPurchasetotal(BigDecimal.ZERO);
				
				Set<PurchaseDetail> purchaseDetails = purchase.getPurchaseDetails().stream().map(purchaseDetail->{
					
					PurchaseDetail newPurchaseDetail = new PurchaseDetail();
					Optional<Product> product = productRepository.findById(purchaseDetail.getProduct().getId());
					
					if(!product.isPresent()) {
						throw new ResourceNotFoundException("Product",purchaseDetail.getProduct().getId());
					}
					
					
					
					BigDecimal totalPerProduct = product.get().getPrice().multiply(BigDecimal.valueOf(purchaseDetail.getPurchaseDetailQuantity()));
					
					purchase.setPurchaseQuantity(purchase.getPurchaseQuantity()+1);

					purchase.setPurchasetotal(purchase.getPurchasetotal().add(totalPerProduct));
				
					newPurchaseDetail.setProduct(product.get());
					newPurchaseDetail.setPurchaseDetailTotalPrice(totalPerProduct);
					newPurchaseDetail.setPurchaseDetailQuantity(purchaseDetail.getPurchaseDetailQuantity());
					return purchaseDetailRepository.save(newPurchaseDetail);

			
				}).collect(Collectors.toSet());
				

				/// generate purchase number
				int intTime = (int) (new Date().getTime()/1000);
				purchase.setPurchaseNo(Integer.toString(intTime));
				purchase.setPurchaseDate(new Date());
				purchase.setPurchaseDetails(purchaseDetails);
				
				Purchase newPurchase = purchaseRepository.save(purchase);
		
				

		payment.setPurchase(newPurchase);
		payment.setPaymentDate(new Date());
		
		//check if use user balance
		if(payment.getPaymentMethod()==1) {
			
			UserAccount userAccount = userAccountRepository.findByUserId(customer.getId())
					.orElseThrow(()->new ResourceNotFoundException("User Account",customer.getId()));
			
			userAccount.setBalance(userAccount.getBalance().subtract(payment.getPaymentAmount()));
			userAccountRepository.save(userAccount);
			
			payment.setPaymentReferenceNumber("UB-".concat(LocalTime.now().toString()));
			
			
		}
		
		return paymentRepository.save(payment);
	}
}
