package com.xyz.schoolManagementSystem.model;

import java.util.List;

public class Student {
    private String rollNo;
    private String name;
    private String dateOfBirth;
    private List<String> subjects;

    // Getters and Setters
    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "rollNo : " + rollNo + '\'' +
                ", name : " + name + '\'' +
                ", dateOfBirth : " + dateOfBirth + '\'' +
                ", subject : " + subjects ;
    }
}
