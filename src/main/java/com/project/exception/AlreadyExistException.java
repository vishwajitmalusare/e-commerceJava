package com.project.exception;

public class AlreadyExistException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;

	
	
	public AlreadyExistException() {}



	public AlreadyExistException(String message) {
		super();
		this.message= message;
//		System.out.println(message);
	}



	public String getMessage() {
		return message;
	}
	
}
