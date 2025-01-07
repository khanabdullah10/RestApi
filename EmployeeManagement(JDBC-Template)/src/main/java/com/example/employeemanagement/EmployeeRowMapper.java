package com.example.employeemanagement;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRowMapper implements RowMapper<Employee> {
    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee employee = new Employee();
        employee.setEmpId(rs.getInt("empId"));
        employee.setEmpName(rs.getString("empName"));
        employee.setAddress(rs.getString("address"));
        employee.setCity(rs.getString("city"));
        employee.setSalary(rs.getDouble("salary"));
        return employee;
    }
}