package com.webapp.ecomerce.api.controller.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.ecomerce.model.Product;
import com.webapp.ecomerce.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@GetMapping()
	public List<Product> getProducts(){
		return productService.getProducts();
		
	}
	
	

}
