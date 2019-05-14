package com.osc.server.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osc.exception.ResourceNotFoundException;
import com.osc.server.model.Payment;
import com.osc.server.model.Purchase;
import com.osc.server.repository.IPaymentRepository;
import com.osc.server.repository.IPurchaseRepository;

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
	
	@PostMapping("/main")
	public Payment doPayment(@RequestBody Payment payment) {
		// check purchase
		Long purchaseID = payment.getPurchase().getId();
		Purchase purchase = purchaseRepository.findById(purchaseID)
				.orElseThrow(()->new ResourceNotFoundException("Purchase", purchaseID));
		
		payment.setPurchase(purchase);
		payment.setPaymentDate(new Date());
		
		return paymentRepository.save(payment);
	}
}
