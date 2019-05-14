package com.osc.server.wrapper;

import javax.persistence.Entity;

import com.osc.server.model.Payment;
import com.osc.server.model.Purchase;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tommy Toban on 13/05/2019.
 */


@Getter
@Setter
public class PurchaseWrapper {
	
	private Purchase purchase;
	private Payment payment;
}
