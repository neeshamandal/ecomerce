package com.webapp.ecomerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.webapp.ecomerce.model.Product;

public interface ProductDAO extends JpaRepository<Product, Long>{

}
