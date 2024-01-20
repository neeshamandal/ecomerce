package com.webapp.ecomerce.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class WebOrderQuantities implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	
	@Column(name = "quantity", nullable = false)
	private Integer quantity;
	
	@JsonIgnore
	@ManyToOne(optional = false)
	@JoinColumn(name = "order_id", nullable = false)
	private WebOrder order;

}
