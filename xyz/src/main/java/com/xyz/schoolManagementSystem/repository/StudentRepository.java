package com.xyz.schoolManagementSystem.repository;

import com.xyz.schoolManagementSystem.model.Student;

import java.util.HashMap;
import java.util.Map;

public class StudentRepository {
    private Map<String, Student> students;

    public StudentRepository() {
        this.students = new HashMap<>();
    }

    public void addStudent(Student student) {
        students.put(student.getRollNo(), student);
    }

    public Student getStudent(String rollNo) {
        return students.get(rollNo);
    }

    public void deleteStudent(String rollNo) {
        students.remove(rollNo);
    }
    public Map<String, Student> getStudents() {
        return students;
    }


}
