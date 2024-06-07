package com.cg.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(EmployeeAlreadyExixtsException.class)
	public ResponseEntity<String> handleEmployeeAlreadyExixtsException(EmployeeAlreadyExixtsException employee){
		return ResponseEntity.status(HttpStatus.CONFLICT).body("employee already exists");
		
	}
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	public  ResponseEntity<String> handleEmployeeNotFoundException(EmployeeNotFoundException employee){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(employee.getMessage());
	}
	
	
	
	
	@ExceptionHandler(DBException.class)
	public ResponseEntity<String> handleDBException(DBException employee){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
	}

	
}
