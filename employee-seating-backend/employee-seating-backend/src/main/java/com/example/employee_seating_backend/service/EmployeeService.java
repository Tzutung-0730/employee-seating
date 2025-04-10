package com.example.employee_seating_backend.service;

import com.example.employee_seating_backend.model.Employee;
import com.example.employee_seating_backend.repository.EmployeeRepository;
import com.example.employee_seating_backend.repository.SeatingChartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final SeatingChartRepository seatingChartRepository;

    public EmployeeService(EmployeeRepository employeeRepository, SeatingChartRepository seatingChartRepository) {
        this.employeeRepository = employeeRepository;
        this.seatingChartRepository = seatingChartRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    public Employee getEmployeeById(String empId) {
        return employeeRepository.getEmployeeById(empId);
    }

    @Transactional
    public int createEmployee(Employee employee) {
        if (employee.getFloorSeatSeq() != null) {
            seatingChartRepository.updateSeatStatusToOccupied(employee.getFloorSeatSeq());
        }

        return employeeRepository.createEmployee(employee);
    }

    @Transactional
    public int updateEmployee(Employee employee) {
        Employee existingEmployee = employeeRepository.getEmployeeById(employee.getEmpId());
        Integer originalFloorSeatSeq = existingEmployee.getFloorSeatSeq();  // 獲取原來的座位號

        if (originalFloorSeatSeq != null) {
            Boolean currentSeatStatus = seatingChartRepository.getSeatStatusByFloorSeatSeq(originalFloorSeatSeq);

            if (Boolean.TRUE.equals(currentSeatStatus)) {
                seatingChartRepository.updateSeatStatusToAvailable(originalFloorSeatSeq);
            }
        }

        int updateEmployeeData = employeeRepository.updateEmployee(employee);

        if (employee.getFloorSeatSeq() != null) {
            seatingChartRepository.updateSeatStatusToOccupied(employee.getFloorSeatSeq());
        }

        return updateEmployeeData;
    }

    @Transactional
    public void deleteEmployee(String empId) {
        Employee employee = employeeRepository.getEmployeeById(empId);

        if (employee.getFloorSeatSeq() != null) {
            Boolean currentSeatStatus = seatingChartRepository.getSeatStatusByFloorSeatSeq(employee.getFloorSeatSeq());

            if (Boolean.TRUE.equals(currentSeatStatus)) {
                seatingChartRepository.updateSeatStatusToAvailable(employee.getFloorSeatSeq());
            }
        }

        employeeRepository.deleteEmployee(empId);
    }
}
