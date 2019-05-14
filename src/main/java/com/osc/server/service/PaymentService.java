package com.osc.server.service;

import java.util.Set;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osc.server.model.Payment;
import com.osc.server.model.Purchase;
import com.osc.server.model.PurchaseDetail;
import com.osc.server.wrapper.PurchaseWrapper;

/**
 * Created by Tommy Toban on 13/05/2019.
 */

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentService extends BaseService<Payment>{
	
	@PostMapping("/dopayment")
	public Purchase doPayment(@RequestBody PurchaseWrapper purchaseWrapper) {
		
		return null;
	}
}
