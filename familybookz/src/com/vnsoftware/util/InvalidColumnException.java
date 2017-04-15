package com.vnsoftware.util;

public class InvalidColumnException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;
	public InvalidColumnException(String message) {
		this.message = message;
	}
	@Override
	public String getMessage() {
		return message + super.getMessage();
	}

}
