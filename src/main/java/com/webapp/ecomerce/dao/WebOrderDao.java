package com.webapp.ecomerce.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webapp.ecomerce.model.LocalUser;
import com.webapp.ecomerce.model.WebOrder;

public interface WebOrderDao extends JpaRepository<WebOrder, Long> {

	public List<WebOrder> findByUser(LocalUser user);
}
