package com.webapp.ecomerce.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class WebOrder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private LocalUser user;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "address_id", nullable = false)
	private Address address;

	@OneToMany(mappedBy = "order", orphanRemoval = true, cascade = CascadeType.REMOVE)
	public List<WebOrderQuantities> webOrderQuantities = new ArrayList<>();

}
