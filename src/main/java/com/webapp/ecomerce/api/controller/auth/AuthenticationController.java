package com.webapp.ecomerce.api.controller.auth;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.ecomerce.api.model.LoginBody;
import com.webapp.ecomerce.api.model.LoginResponse;
import com.webapp.ecomerce.api.model.RegistrationBody;
import com.webapp.ecomerce.exception.EmailFailureException;
import com.webapp.ecomerce.exception.UserAlreadyExsistsException;
import com.webapp.ecomerce.exception.UserNotVerifiedException;
import com.webapp.ecomerce.model.LocalUser;
import com.webapp.ecomerce.security.JWTRequestFilter;
import com.webapp.ecomerce.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	@Autowired
	UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registrationBody){
		try {
			userService.registerUser(registrationBody);
			return ResponseEntity.ok().build();
			
		} catch (UserAlreadyExsistsException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
			
		} catch (EmailFailureException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody) throws UserNotVerifiedException{
		String jwt = null;
		try {
			jwt = userService.loginUser(loginBody);
			
		} catch (UserNotVerifiedException ex) {
			LoginResponse response = new LoginResponse();
			response.setSuccess(false);
			String reason = "USER_NOT_VERIFIED ";
			
			if(ex.isNewEmailSent()) {
				reason += "EMAIL_RESENT";
			}
			
			response.setFailureReason(reason);
			System.out.println(response);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
			
		} catch (EmailFailureException ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		if(jwt==null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}else {
			LoginResponse response = new LoginResponse();
			response.setJwt(jwt);
			response.setSuccess(true);
			return ResponseEntity.ok(response);
		}
	}
	
	
	@PostMapping("/verify")
	public ResponseEntity verifyEmail(@RequestParam String token) {
		if(userService.verifyUser(token)) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}else {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	
	@GetMapping("/me")
	public LocalUser getLoggedInUserProfile(@AuthenticationPrincipal LocalUser user) {
		return user;	
	}

}
