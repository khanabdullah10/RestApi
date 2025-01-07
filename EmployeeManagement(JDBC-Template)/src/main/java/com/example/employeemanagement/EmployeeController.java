package com.example.employeemanagement;

import java.util.List;
import java.util.Scanner;

public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final Scanner scanner;

    public EmployeeController(EmployeeRepository employeeRepository, Scanner scanner) {
        this.employeeRepository = employeeRepository;
        this.scanner = scanner;
    }

    public void addEmployee() {
        Employee employee = new Employee();
        System.out.print("Enter Employee Name: ");
        employee.setEmpName(scanner.nextLine());
        System.out.print("Enter Address: ");
        employee.setAddress(scanner.nextLine());
        System.out.print("Enter City: ");
        employee.setCity(scanner.nextLine());
        System.out.print("Enter Salary: ");
        employee.setSalary(scanner.nextDouble());
        scanner.nextLine();

        employeeRepository.addEmployee(employee);
        System.out.println("[Employee added successfully.]");
    }

    public void displayAllEmployees() {
        List<Employee> employees = employeeRepository.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("[Oops! No employees found.]");
        } else {
            employees.forEach(emp -> System.out.println(emp.getEmpId() + " " + emp.getEmpName()));
        }
    }

    public void getEmployeeById() {
        System.out.print("Enter Employee ID: ");
        int empId = scanner.nextInt();
        scanner.nextLine();
        Employee employee = employeeRepository.getEmployeeById(empId);
        if (employee != null) {
            System.out.println("Employee Found: " + employee.getEmpName());
        } else {
            System.out.println("Employee not found.");
        }
    }

    public void getEmployeesByCityAndSalary() {
        System.out.print("Enter City: ");
        String city = scanner.nextLine();
        System.out.print("Enter Salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine();

        List<Employee> filteredEmployees = employeeRepository.getEmployeesByCityAndSalary(city, salary);
        if (filteredEmployees.isEmpty()) {
            System.out.println("[No employees found in " + city + " with a salary greater than " + salary + ".]");
        } else {
            filteredEmployees.forEach(emp -> System.out.println(emp.getEmpId() + " " + emp.getEmpName()));
        }
    }

    public void updateEmployeeSalary() {
        try {
            System.out.print("Enter Employee ID to update salary: ");
            int empId = scanner.nextInt();
            scanner.nextLine();

            Employee employee = employeeRepository.getEmployeeById(empId);
            if (employee == null) {
                System.out.println("Error: Employee ID not found.");
                return;
            }

            System.out.print("Enter percentage increase (e.g., enter 10 for 10%): ");
            double percentage = scanner.nextDouble();
            scanner.nextLine();

            employeeRepository.updateEmployeeSalary(empId, percentage);
            System.out.println("[Employee salary updated successfully.]");
        } catch (Exception e) {
            System.out.println("Error updating employee salary: " + e.getMessage());
        }
    }

    public void deleteEmployee() {
        System.out.print("Enter Employee ID to delete: ");
        while (true) {
            try {
                int empId = Integer.parseInt(scanner.nextLine());
                Employee employee = employeeRepository.getEmployeeById(empId);
                if (employee != null) {
                    employeeRepository.deleteEmployeeById(empId);
                    System.out.println("[Employee deleted successfully.]");
                } else {
                    System.out.println("[Employee with ID " + empId + " does not exist.]");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid Employee ID.");
            } catch (Exception e) {
                System.out.println("Error deleting employee: " + e.getMessage());
                break;
            }
        }
    }

    public void printAllEmployeeDetails(){
        employeeRepository.printAllEmployeeDetails();
    }
}