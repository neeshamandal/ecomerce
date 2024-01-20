package com.webapp.ecomerce.api.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SendEmailBody {
	@NotNull
	@NotBlank
	@Email
	private String to;
	
	@NotNull
	@NotBlank
	@Email
	private String ccSender;
	
	@NotNull
	@NotBlank
	@Email
	private String bccSender;
	
	@NotBlank
	private String emailSubject;
	
	private String message;

}
