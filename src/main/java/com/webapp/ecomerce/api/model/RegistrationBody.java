package com.webapp.ecomerce.api.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RegistrationBody {
	@NotNull
	@NotBlank
	@Size(min=3, message = "{validation.name.size.too_short}")
	@Size(max= 255, message = "{validation.size.too_long}")
	private String username;
	
	@NotNull
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	@NotNull
	@Size(min=6, max=32, message = "{password must be at least 6 character with one uppercase letter and one number}")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")
	private String password;
	
	@NotBlank
	@NotNull
	private String firstName;
	
	@NotBlank
	@NotNull
	private String lastName;

}
