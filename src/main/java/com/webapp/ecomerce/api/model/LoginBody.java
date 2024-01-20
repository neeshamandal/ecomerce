 package com.webapp.ecomerce.api.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LoginBody{
	
	@NotNull
	@NotBlank
	private String username;
	@NotNull
	@NotBlank
	private String password;

}
