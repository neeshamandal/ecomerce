package com.webapp.ecomerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.ecomerce.dao.ProductDAO;
import com.webapp.ecomerce.model.Product;

@Service
public class ProductService {
	@Autowired
	ProductDAO productDao;
	
	public List<Product> getProducts(){
		return productDao.findAll();
	}

}
