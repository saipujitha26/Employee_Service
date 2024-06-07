package com.cg.employee.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.employee.entity.Employee;
import com.cg.employee.service.EmployeeService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	private EmployeeService employeeService;
	
	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@GetMapping("/excel")
	public void generateExcelReport(HttpServletResponse response)throws Exception {
		
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=employee.xls";
		response.setHeader(headerKey, headerValue);
		employeeService.generateExcel(response);
		
	}
	
	
	
	@PostMapping("/")
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
			Employee newEmployee = employeeService.createEmployee(employee);
			return ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
	}
	
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long employeeId){
		Optional<Employee> employee = Optional.of(employeeService.getEmployeeById(employeeId));
			return new ResponseEntity<Employee>(employee.get(), HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Employee>> getAllEmployee(){
		List<Employee> employee = employeeService.getAllEmployee();
		return ResponseEntity.status(HttpStatus.OK).body(employee);	
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long employeeId){
		employeeService.deleteEmployee(employeeId);
		return ResponseEntity.status(HttpStatus.OK).body("Employee deleted Successfully..");
	}
	
	@GetMapping("/project/{projectId}")
    public ResponseEntity<List<Employee>> getEmployeesByProjectId(@PathVariable("projectId") Long projectId) {
        List<Employee> employees = employeeService.getEmployeesByProjectId(projectId);
        return ResponseEntity.ok(employees);
    }


}
