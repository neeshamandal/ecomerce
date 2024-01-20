package com.webapp.ecomerce.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webapp.ecomerce.model.LocalUser;
import com.webapp.ecomerce.model.VerificationToken;

public interface VerificationTokenDao extends JpaRepository<VerificationToken, Long>{
	Optional<VerificationToken> findByToken(String token);
	
	void deleteByUser(LocalUser user);

}
