package com.vijay.authservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthUserApiException extends RuntimeException{
	
	private HttpStatus status;
	private String message;
	public AuthUserApiException(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

}
