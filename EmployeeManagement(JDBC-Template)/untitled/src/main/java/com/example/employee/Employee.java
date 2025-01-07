package com.example.employee;

public class Employee {
    private int empId;
    private String empName;
    private String address;
    private String city;
    private double salary;

    public Employee(int empId, String empName, String address, String city, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.address = address;
        this.city = city;
        this.salary = salary;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", salary=" + salary +
                '}';
    }
}
