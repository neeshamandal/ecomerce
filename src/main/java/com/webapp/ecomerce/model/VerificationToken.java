package com.webapp.ecomerce.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class VerificationToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "token", nullable = false, unique = true)
	private String token;
	
	@Column(name = "created_timestamp", nullable = false)
	private Timestamp createdTimestamp;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private LocalUser user;
}
