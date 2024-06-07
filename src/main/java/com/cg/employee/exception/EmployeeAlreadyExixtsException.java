package com.cg.employee.exception;

import com.cg.employee.entity.Employee;

public class EmployeeAlreadyExixtsException extends RuntimeException {
	
	public EmployeeAlreadyExixtsException(String message) {
		super(message);
	}

}
