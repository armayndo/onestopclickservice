package com.osc.server.service;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osc.server.model.Product;

/**
 * Created by Tommy Toban on 25/04/2019.
 */
@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("/api/v1/product")
public class ProductService extends BaseService<Product> {

}
