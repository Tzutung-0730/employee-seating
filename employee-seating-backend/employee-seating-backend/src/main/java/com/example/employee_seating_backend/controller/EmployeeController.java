package com.example.employee_seating_backend.controller;

import com.example.employee_seating_backend.model.Employee;
import com.example.employee_seating_backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // 查詢所有員工資料
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    // 查詢指定員工資料
    @GetMapping("/{empId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String empId) {
        Employee employee = employeeService.getEmployeeById(empId);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 新增員工資料
    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) {
        try {
            int result = employeeService.createEmployee(employee);
            if (result > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Employee created successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create employee");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating employee: " + e.getMessage());
        }
    }

    // 更新員工資料
    @PutMapping("/{empId}")
    public ResponseEntity<String> updateEmployee(@PathVariable String empId, @RequestBody Employee employee) {
        try {
            employee.setEmpId(empId);
            int result = employeeService.updateEmployee(employee);
            if (result > 0) {
                return ResponseEntity.ok("Employee updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update employee");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating employee: " + e.getMessage());
        }
    }

    // 刪除員工資料
    @DeleteMapping("/{empId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String empId) {
        try {
            employeeService.deleteEmployee(empId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting employee: " + e.getMessage());
        }
    }
}
