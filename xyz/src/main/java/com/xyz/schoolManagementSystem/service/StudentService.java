package com.xyz.schoolManagementSystem.service;

import com.xyz.schoolManagementSystem.model.Student;
import com.xyz.schoolManagementSystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return new ArrayList<>(studentRepository.getStudents().values());
    }

    public Student addStudent(Student student) {
        studentRepository.addStudent(student);
        return student;
    }

    public Student getStudent(String rollNo) {
        return studentRepository.getStudent(rollNo);
    }

    public void deleteStudent(String rollNo) {
        studentRepository.deleteStudent(rollNo);
    }
}
