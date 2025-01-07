package com.studentManagement.student.service;


import com.studentManagement.student.entity.Student;
import com.studentManagement.student.exception.ApplicationException;
import com.studentManagement.student.studentRepository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Add Student with exception handling
    public Student addStudent(Student student) {
        try {
            return studentRepository.save(student);
        } catch (Exception e) {
            throw new ApplicationException("Failed to add student: " + e.getMessage());
        }
    }


    // Get all students with exception handling
    public List<Student> getAllStudents() {
        try {
            List<Student> students = studentRepository.findAll();
            if (students.isEmpty()) {
                throw new ApplicationException("No students found in the database.");
            }
            return students;
        } catch (Exception e) {
            throw new ApplicationException("Failed to retrieve students: " + e.getMessage());
        }
    }

    // Get student by ID with exception handling
    public Optional<Student> getStudentById(Integer studentId) {
        try {
            Optional<Student> student = studentRepository.findById(studentId);
            if (!student.isPresent()) {
                throw new ApplicationException("Student with ID " + studentId + " not found.");
            }
            return student;
        } catch (Exception e) {
            throw new ApplicationException("Failed to retrieve student: " + e.getMessage());
        }
    }

    // Get students by city and CGPA with exception handling
    public List<Student> getStudentsByCityAndCgpa(String city, Double cgpa) {
        try {
            List<Student> students = studentRepository.findByCityAndCgpaGreaterThan(city, cgpa);
            if (students.isEmpty()) {
                throw new ApplicationException("No students found in " + city + " with CGPA greater than " + cgpa);
            }
            return students;
        } catch (Exception e) {
            throw new ApplicationException("Failed to retrieve students: " + e.getMessage());
        }
    }

    // Reduce fees for students with CGPA = 10
    public void reduceFeesForTopStudents() {
        try {
            List<Student> students = studentRepository.findAll();
            for (Student student : students) {
                if (student.getCgpa() == 10) {
                    student.setCourse_fees(student.getCourse_fees() / 2);
                    studentRepository.save(student);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException("Failed to reduce fees: " + e.getMessage());
        }
    }

    // Delete student by ID with exception handling
    public void deleteStudentById(Integer studentId) {
        try {
            studentRepository.deleteById(studentId);
        } catch (EmptyResultDataAccessException e) {
            throw new ApplicationException("Student with ID " + studentId + " not found for deletion.");
        } catch (Exception e) {
            throw new ApplicationException("Failed to delete student: " + e.getMessage());
        }
    }
}
