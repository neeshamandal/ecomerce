package com.webapp.ecomerce.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Address implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "address_line_1", nullable = false)
	private String addressLine1;
	
	@Column(name = "address_line_2")
	private String addressLine2;
	
	@Column(name ="city", nullable = false)
	private String city;
	
	@Column(name="country", nullable = false)
	private String country;
	
	@JsonIgnore
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id",nullable = false)
	private LocalUser user;

	@Override
	public String toString() {
		return "Address [id=" + id + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", city="
				+ city + ", country=" + country + "]";
	}

}
