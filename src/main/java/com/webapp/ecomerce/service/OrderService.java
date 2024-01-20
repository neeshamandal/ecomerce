package com.webapp.ecomerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.ecomerce.dao.WebOrderDao;
import com.webapp.ecomerce.model.LocalUser;
import com.webapp.ecomerce.model.WebOrder;

@Service
public class OrderService {
	
	@Autowired
	WebOrderDao webOrderDao;
	
	public List<WebOrder> getOrders(LocalUser user){
		return webOrderDao.findByUser(user);
	}

}
