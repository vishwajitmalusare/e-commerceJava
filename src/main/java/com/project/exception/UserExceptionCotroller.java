package com.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.project.response.Responsehandler;

@ControllerAdvice
public class UserExceptionCotroller {

	@ExceptionHandler(value = RecordNotFoundException.class)
	public ResponseEntity<Object> exception(RecordNotFoundException exception){
		return Responsehandler.generatedResponse(exception.getMessage(), HttpStatus.NOT_FOUND, null, true);
	}
	
	@ExceptionHandler(value = AlreadyExistException.class)
	public ResponseEntity<Object> exception(AlreadyExistException exception1){
		return Responsehandler.generatedResponse(exception1.getMessage(), HttpStatus.ALREADY_REPORTED, null ,true);
	}
}
