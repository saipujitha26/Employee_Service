package com.cg.employee.exception;

public class EmployeeNotFoundException extends RuntimeException{
	
	public EmployeeNotFoundException(String message) {
		super(message);
	}
}
