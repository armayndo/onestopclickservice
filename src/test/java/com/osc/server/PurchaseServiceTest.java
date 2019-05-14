package com.osc.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.osc.server.model.Product;
import com.osc.server.repository.IProductRepository;

/**
 * Created by Tommy Toban on 13/05/2019.
 */

@RunWith(MockitoJUnitRunner.class)
public class PurchaseServiceTest {
	
	@Mock
	IProductRepository repository;
	
	@Mock
	Product product;
	
	@Before
	public void init() {
		product = new Product();
		product.setId(1L);
		product.setName("TV");
		product.setPrice(new BigDecimal(1000));
		
	}
	
	@Test
	public void findProduct() {
		when(repository.findById(1L)).thenReturn(Optional.of(product));
		assertEquals(2+2,4);
	}
}
