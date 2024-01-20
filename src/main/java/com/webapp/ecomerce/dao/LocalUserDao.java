package com.webapp.ecomerce.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ecomerce.model.LocalUser;
@Repository
public interface LocalUserDao extends JpaRepository<LocalUser, Long>{
	
	Optional< LocalUser> findByUsernameIgnoreCase(String username);
	
	Optional<LocalUser> findByEmailIgnoreCase(String email);
	
}
