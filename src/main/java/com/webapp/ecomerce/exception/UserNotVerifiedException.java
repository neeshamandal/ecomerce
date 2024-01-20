package com.webapp.ecomerce.exception;

import lombok.Data;

@Data
public class UserNotVerifiedException extends Exception{
	
	private boolean newEmailSent;
	
	public UserNotVerifiedException(boolean newEmailSent) {
		this.newEmailSent = newEmailSent;
	}
	
	 public boolean isNewEmailSent() {
		    return newEmailSent;
		  }

}
