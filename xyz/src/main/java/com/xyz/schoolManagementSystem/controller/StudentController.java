package com.xyz.schoolManagementSystem.controller;

import com.xyz.schoolManagementSystem.exception.InvalidInputException;
import com.xyz.schoolManagementSystem.model.Student;
import com.xyz.schoolManagementSystem.service.StudentService;
import com.xyz.schoolManagementSystem.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Get a student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") String studentId) {
        Student student = studentService.getStudentById(studentId);
        return ResponseEntity.ok(student);
    }

    // Create a new student
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        // Assume a method to validate the student object
        validateStudent(student);
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    // Update a student's birth date
    @PutMapping("/{id}/birthdate")
    public ResponseEntity<Void> updateStudentBirthDate(@PathVariable("id") String studentId, @RequestBody String birthDate) {
        studentService.setStudentBirthDate(studentId, birthDate);
        return ResponseEntity.noContent().build();
    }

    // Validate student object (example method)
    private void validateStudent(Student student) {
        if (!ValidationUtil.isNotEmpty(student.getName())) {
            throw new InvalidInputException("Student name cannot be empty.");
        }
        // Additional validation logic can be added here
    }
}
