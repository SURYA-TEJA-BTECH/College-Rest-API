package com.surya.exceptions;

import com.auth0.jwt.exceptions.JWTVerificationException;

public class InvalidTokenException extends RuntimeException {

	public InvalidTokenException(String string, JWTVerificationException e) {
		super(string);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
