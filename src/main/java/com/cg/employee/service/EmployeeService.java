package com.cg.employee.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cg.employee.entity.Employee;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public interface EmployeeService {
	
	public void generateExcel(HttpServletResponse response) throws IOException;
	
	public Employee createEmployee(Employee employee);
	
	public Employee getEmployeeById(Long employeeId);
	
	public List<Employee> getAllEmployee();
	
	public String deleteEmployee(Long employeeId);
	
	public List<Employee> getEmployeesByProjectId(Long projectId);

}
