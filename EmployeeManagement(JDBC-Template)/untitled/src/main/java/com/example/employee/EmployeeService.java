package com.example.employee;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class EmployeeService {
    private EmployeeDAO dao;
    Scanner scanner;

    public EmployeeService() throws SQLException {
        dao = new EmployeeDAO();
        scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("Menu:");
        System.out.println("1) Add Employee");
        System.out.println("2) Get Employee");
        System.out.println("3) Update Employee Salary");
        System.out.println("4) Delete Employee");
        System.out.print("Choose an option: ");
    }

    public void addEmployee() throws SQLException {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter city: ");
        String city = scanner.nextLine();
        System.out.print("Enter salary: ");
        double salary = scanner.nextDouble();
        dao.addEmployee(new Employee(0, name, address, city, salary));
        System.out.println("Employee added successfully!");
    }

    public void getEmployeeMenu() throws SQLException {
        System.out.println("1) Get All Employees");
        System.out.println("2) Get Employee By ID");
        System.out.println("3) Get Employees By City and Salary");
        System.out.print("Choose an option: ");
        int option = scanner.nextInt();

        switch (option) {
            case 1:
                List<Employee> employees = dao.getAllEmployees();
                employees.forEach(System.out::println);
                break;
            case 2:
                System.out.print("Enter Employee ID: ");
                int empId = scanner.nextInt();
                Employee employee = dao.getEmployeeById(empId);
                System.out.println(employee);
                break;
            case 3:
                scanner.nextLine();  // Consume newline
                System.out.print("Enter city: ");
                String city = scanner.nextLine();
                System.out.print("Enter minimum salary: ");
                double salary = scanner.nextDouble();
                List<Employee> filteredEmployees = dao.getEmployeesByCityAndSalary(city, salary);
                filteredEmployees.forEach(System.out::println);
                break;
            default:
                System.out.println("Invalid option!");
                break;
        }
    }

    public void updateEmployeeSalary() throws SQLException {
        System.out.print("Enter Employee ID: ");
        int empId = scanner.nextInt();
        dao.updateEmployeeSalary(empId, 10);
        System.out.println("Employee salary updated by 10%!");
    }

    public void deleteEmployee() throws SQLException {
        System.out.print("Enter Employee ID to delete: ");
        int empId = scanner.nextInt();
        dao.deleteEmployeeById(empId);
        System.out.println("Employee deleted successfully!");
    }
}
