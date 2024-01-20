package com.webapp.ecomerce.api.controller.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.ecomerce.model.LocalUser;
import com.webapp.ecomerce.model.WebOrder;
import com.webapp.ecomerce.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	OrderService orderService;
	
	@GetMapping("/get")
	public List<WebOrder> getOrders(@AuthenticationPrincipal LocalUser user){
		return orderService.getOrders(user);
	}

}
