package com.example.employee_seating_backend.repository;

import com.example.employee_seating_backend.model.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Employee> getAllEmployees() {
        String sql = "CALL get_all_employees()";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Employee employee = new Employee();
            employee.setEmpId(rs.getString("emp_id"));
            employee.setName(rs.getString("name"));
            employee.setEmail(rs.getString("email"));
            employee.setFloorSeatSeq(rs.getInt("floor_seat_seq"));
            return employee;
        });
    }

    public Employee getEmployeeById(String empId) {
        String sql = "CALL get_employee_by_id(?)";
        return jdbcTemplate.queryForObject(sql, new Object[]{empId}, (rs, rowNum) -> {
            Employee employee = new Employee();
            employee.setEmpId(rs.getString("emp_id"));
            employee.setName(rs.getString("name"));
            employee.setEmail(rs.getString("email"));
            employee.setFloorSeatSeq(rs.getInt("floor_seat_seq"));
            return employee;
        });
    }

    public int createEmployee(Employee employee) {
        String sql = "CALL insert_employee(?, ?, ?, ?)";
        return jdbcTemplate.update(sql, employee.getEmpId(), employee.getName(), employee.getEmail(), employee.getFloorSeatSeq());
    }

    public int updateEmployee(Employee employee) {
        String sql = "CALL update_employee(?, ?, ?, ?)";
        return jdbcTemplate.update(sql, employee.getEmpId(), employee.getName(), employee.getEmail(), employee.getFloorSeatSeq());
    }

    public void deleteEmployee(String empId) {
        String sql = "CALL delete_employee(?)";
        jdbcTemplate.update(sql, empId);
    }
}
