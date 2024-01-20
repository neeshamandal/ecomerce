package com.webapp.ecomerce.api.controller.product;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.ecomerce.api.model.SendEmailBody;
import com.webapp.ecomerce.service.SendGridService;

@RestController
@RequestMapping("/twillio")
public class SendGridController {
	@Autowired
	SendGridService sendGridService;
	
	@PostMapping("/sendgrid")
	public String sendEmail(@RequestBody SendEmailBody sendEmailBody) throws IOException {
		 return sendGridService.sendEmail(sendEmailBody);
		
	}

}
