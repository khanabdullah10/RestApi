package com.example.employee;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {



        try {
            EmployeeService service = new EmployeeService();
            boolean exit = false;
            while (!exit) {
                service.displayMenu();
                int choice = service.scanner.nextInt();
                service.scanner.nextLine(); // Consume newline
                switch (choice) {
                    case 1 -> service.addEmployee();
                    case 2 -> service.getEmployeeMenu();
                    case 3 -> service.updateEmployeeSalary();
                    case 4 -> service.deleteEmployee();
                    default -> {
                        System.out.println("Invalid option, exiting.");
                        exit = true;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
