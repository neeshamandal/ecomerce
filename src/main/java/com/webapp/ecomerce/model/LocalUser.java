package com.webapp.ecomerce.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.UniqueElements;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
public class LocalUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "username" ,unique = true,nullable = false)
	private String username;
	
	@JsonIgnore
	@Column(name = "password",nullable = false)
	private String password;
	
	@Column(name = "email",unique = true,nullable = false)
	private String email;
	
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	@Column(name="last_name",nullable = false)
	private String lastName;
	
	@JsonIgnore
	@OneToMany(orphanRemoval = true, cascade = CascadeType.REMOVE, mappedBy = "user", fetch = FetchType.EAGER)
	private List<Address> addresses = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@OrderBy("id desc")
	private List<VerificationToken> verificationTokens = new ArrayList<>();
	
	@Column(name = "email_verified", nullable = false)
	private Boolean emailVerified = false;

	@Override
	public String toString() {
		return "LocalUser [id=" + id + ", username=" + username + ", email=" + email + ", firstName=" + firstName
				+ ", lastName=" + lastName + "]";
	}
	
	

}
