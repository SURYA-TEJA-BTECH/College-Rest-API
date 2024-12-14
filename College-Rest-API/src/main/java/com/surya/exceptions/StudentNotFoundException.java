package com.surya.exceptions;

public class StudentNotFoundException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;

	public StudentNotFoundException(String message) {
		super(message);
	}

	public static StudentNotFoundException forId(Long id) {
		return new StudentNotFoundException("student not found with id " + id);
	}

}
