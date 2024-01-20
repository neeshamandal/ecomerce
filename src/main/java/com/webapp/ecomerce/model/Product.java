package com.webapp.ecomerce.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.UniqueElements;

import lombok.Data;

@Data
@Entity
public class Product implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "name", unique = true, nullable = false)
	private String name;
	
	@Column(name = "short_description", nullable = false)
	private String shortDescription;
	
	@Column(name = "long_description")
	private String longDescription;
	
	@Column(name = "price", nullable = false)
	private Double price;

	@OneToOne(mappedBy = "product", cascade = CascadeType.REMOVE, optional = false, orphanRemoval = true)
	private Inventory inventory;

}
