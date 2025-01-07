package com.example.employeemanagement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DataAccessException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addEmployee(Employee employee) {
        String sql = "INSERT INTO Employee (empName, address, city, salary) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, employee.getEmpName(), employee.getAddress(), employee.getCity(), employee.getSalary());
        } catch (DataAccessException e) {

            System.err.println("Error adding employee: " + e.getMessage());

        }
    }

    public List<Employee> getAllEmployees() {
        String sql = "SELECT * FROM Employee";
        try {
            return jdbcTemplate.query(sql, new EmployeeRowMapper());
        } catch (DataAccessException e) {
            System.err.println("Error retrieving all employees: " + e.getMessage());
            return List.of();
        }
    }

    public Employee getEmployeeById(int empId) {
        String sql = "SELECT * FROM Employee WHERE empId = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{empId}, new EmployeeRowMapper());
        } catch (DataAccessException e) {
            System.err.println("Error retrieving employee with ID " + empId + ": " + e.getMessage());
            return null;
        }
    }

    public List<Employee> getEmployeesByCityAndSalary(String city, double salary) {
        String sql = "SELECT * FROM Employee WHERE city = ? AND salary > ?";
        try {
            return jdbcTemplate.query(sql, new Object[]{city, salary}, new EmployeeRowMapper());
        } catch (DataAccessException e) {
            System.err.println("Error retrieving employees by city and salary: " + e.getMessage());
            return List.of();
        }
    }

    public void updateEmployeeSalary(int empId, double percentage) {
        String sql = "UPDATE Employee SET salary = salary + (salary * ? / 100) WHERE empId = ?";
        try {
            jdbcTemplate.update(sql, percentage, empId);
        } catch (DataAccessException e) {
            System.err.println("Error updating salary for employee ID " + empId + ": " + e.getMessage());
        }
    }

    public void deleteEmployeeById(int empId) {
        String sql = "DELETE FROM Employee WHERE empId = ?";
        try {
            jdbcTemplate.update(sql, empId);
        } catch (DataAccessException e) {
            System.err.println("Error deleting employee with ID " + empId + ": " + e.getMessage());
        }
    }

    public void printAllEmployeeDetails() {
        List<Employee> employees = getAllEmployees();
        System.out.println("Employee ID\t Name\t\t Address \t\t City \t\t Salary");
        System.out.println("----------------------------------------------------------------------------------");
        for (Employee emp : employees) {
            System.out.printf("%d \t\t %s\t\t%s\t\t%s\t\t%.2f%n",
                    emp.getEmpId(),
                    emp.getEmpName(),
                    emp.getAddress(),
                    emp.getCity(),
                    emp.getSalary());
        }
    }


}