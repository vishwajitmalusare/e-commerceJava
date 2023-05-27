package com.project.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Responsehandler {
	
	public static ResponseEntity<Object> generatedResponse(String message, HttpStatus status, Object responseObj){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", message);
		map.put("status", status);
		map.put("data", responseObj);
		return new ResponseEntity<Object>(map,status);
	}
	
public static ResponseEntity<Object> generatedResponse(String message, HttpStatus status, Object responseObj, boolean error){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", message);
		map.put("status", status);
		map.put("data", responseObj);
		map.put("error", error);
		return new ResponseEntity<Object>(map,status);
	}
}
