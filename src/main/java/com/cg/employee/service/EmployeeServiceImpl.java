package com.cg.employee.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import com.cg.employee.entity.Employee;
import com.cg.employee.exception.DBException;
import com.cg.employee.exception.EmployeeAlreadyExixtsException;
import com.cg.employee.exception.EmployeeNotFoundException;
import com.cg.employee.repository.EmployeeRepository;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	private final EmployeeRepository employeeRepository;
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository){
		this.employeeRepository=employeeRepository;
	}
	

	public void generateExcel(HttpServletResponse response) throws IOException {
		List<Employee> employee = employeeRepository.findAll();
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Employee_details");
		HSSFRow row = sheet.createRow(0);
		
		row.createCell(0).setCellValue("employeeId");
		row.createCell(1).setCellValue("projectId");
		row.createCell(2).setCellValue("name");
		row.createCell(3).setCellValue("email");
		row.createCell(4).setCellValue("experience");
		row.createCell(5).setCellValue("salary");
		
		int dataRowIndex=1;
		for(Employee e : employee) {
			HSSFRow dataRow = sheet.createRow(dataRowIndex);
			dataRow.createCell(0).setCellValue(e.getEmployeeId());
			dataRow.createCell(1).setCellValue(e.getProjectId());
			dataRow.createCell(2).setCellValue(e.getName());
			dataRow.createCell(3).setCellValue(e.getEmail());
			dataRow.createCell(4).setCellValue(e.getExperience());
			dataRow.createCell(5).setCellValue(e.getSalary());
			dataRowIndex ++;
		}
		
		ServletOutputStream ops = response.getOutputStream();
		workbook.write(ops);
		workbook.close();
		ops.close();
		
	}

	@Override
	public Employee createEmployee(Employee employee) {
		Optional<Employee> employeeFromDb = employeeRepository.findById(employee.getEmployeeId());
		Employee newEmployee;
		if(!employeeFromDb.isEmpty()) {
			throw new EmployeeAlreadyExixtsException("Employee Altready Exists..");
		}
		try {
			newEmployee = employeeRepository.save(employee);
		}
			catch (Exception ex) {
				throw new DBException("DB Exception or Internal server error");
			}
		
		return newEmployee;
	}

	
	
	@Override
	public Employee getEmployeeById(Long employeeId) {
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if(employee.isEmpty()) {
			throw new EmployeeNotFoundException("Employee not found with the given id");
		}
		return employee.get();
		
	}
	
	
	@Override
	public String deleteEmployee(Long employeeId) {
	Optional<Employee> emp = employeeRepository.findById(employeeId);
	if(emp.isEmpty()) {
		throw new EmployeeNotFoundException("Employee not found");
	}
		employeeRepository.deleteById(employeeId);
		return "Employee deleted successfully";
	}

	@Override
	public List<Employee> getAllEmployee() {
		List<Employee> employee=employeeRepository.findAll();
		if(employee.isEmpty()) {
			throw new EmployeeNotFoundException("Employee not found in DB");
		}
		return employee;
	}

	@Override
	public List<Employee> getEmployeesByProjectId(Long projectId) {
		List<Employee> employee = employeeRepository.findByProjectId(projectId);
		if(employee.isEmpty()) {
			throw new EmployeeNotFoundException("Employee not found with given project id");
		}
		return employee;
		
	}

}
