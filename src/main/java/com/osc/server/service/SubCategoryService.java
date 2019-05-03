package com.osc.server.service;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osc.server.model.SubCategory;

/**
 * Created by Tommy Toban on 02/05/2019.
 */

@RestController
@RequestMapping("/api/v1/subcategory")
public class SubCategoryService extends BaseService<SubCategory>{

}
