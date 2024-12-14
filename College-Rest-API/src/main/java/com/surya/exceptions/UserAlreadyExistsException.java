package com.surya.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsException(String string) {
		super(string);

	}

}
