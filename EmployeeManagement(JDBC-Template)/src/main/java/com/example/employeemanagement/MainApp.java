package com.example.employeemanagement;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class MainApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static EmployeeController employeeController;

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(JdbcConfig.class);
        EmployeeRepository employeeRepository = context.getBean(EmployeeRepository.class);
        employeeController = new EmployeeController(employeeRepository, scanner);

        while (true) {
            displayMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> employeeController.addEmployee();
                case 2 -> displayGetEmployeeMenu();
                case 3 -> employeeController.updateEmployeeSalary();
                case 4 -> employeeController.deleteEmployee();
                case 5 -> {
                    System.out.println("!!! Hasta la vista !!!");
                    System.exit(0);
                }
                default -> System.out.println(" ! Invalid option. Please try again. ! ");
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Menu:");
        System.out.println("1) Add Employee");
        System.out.println("2) Get Employee");
        System.out.println("3) Update Employee Salary");
        System.out.println("4) Delete Employee");
        System.out.println("5) Exit");
        System.out.print("Choose an option: ");
    }

    private static void displayGetEmployeeMenu() {
        System.out.println("1) Get All Employees");
        System.out.println("2) Get Employee By ID");
        System.out.println("3) Get Employees By City and Salary");
        System.out.println("4) Print All Employee Details");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> employeeController.displayAllEmployees();
            case 2 -> employeeController.getEmployeeById();
            case 3 -> employeeController.getEmployeesByCityAndSalary();
            case 4 -> employeeController.printAllEmployeeDetails();
            default -> System.out.println(" ! Invalid option. Please try again. ! ");
        }
    }
}