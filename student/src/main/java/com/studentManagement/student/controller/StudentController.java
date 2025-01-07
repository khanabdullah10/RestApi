package com.studentManagement.student.controller;
import com.studentManagement.student.entity.Student;
import com.studentManagement.student.exception.ApplicationException;
import com.studentManagement.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Add Student
    @PostMapping("/add")
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        try {
            studentService.addStudent(student);
            return ResponseEntity.status(201).body("Student added Successfully");
        } catch (ApplicationException ex) {
            // Catch the exception and send a custom error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add student"); // Specific message for adding student failure
        }
    }


    // Get All Students
    @GetMapping("/view")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(students); // 200 OK
    }

    // Get Student by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Integer studentId) {
        try {
            // Try to find the student by ID
            Optional<Student> student = studentService.getStudentById(studentId);

            // Case 1: No student found (return 404 with message)
            if (student.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Student not found");  // Ensure the message is included
            }

            // Case 2: If student found, return 200 OK with student data
            return ResponseEntity.ok(student.get());

        } catch (ApplicationException ex) {
            // Case 3: Any unexpected error should return a 500 error with an appropriate message
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to retrieve student: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse); // Ensure error message is returned
        }
    }





    // Get Students by City and CGPA
    @GetMapping("/city-cgpa")
    public ResponseEntity<List<Student>> getStudentsByCityAndCgpa(
            @RequestParam("city") String city,
            @RequestParam("cgpa") Double cgpa) {
        List<Student> students = studentService.getStudentsByCityAndCgpa(city, cgpa);
        if (students.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(students); // 200 OK
    }

    // Reduce Fees for Students with CGPA = 10
    @PutMapping("/reduce-fees")
    public ResponseEntity<String> reduceFeesForTopStudents() {
        studentService.reduceFeesForTopStudents();
        return ResponseEntity.ok("Fees reduced for students with CGPA = 10"); // 200 OK
    }

    // Delete Student by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") Integer studentId) {
        studentService.deleteStudentById(studentId);
        return ResponseEntity.ok("Deleted successfully"); // 204 No Content
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<String> handleApplicationException(ApplicationException ex) {
        String errorMessage = "Failed to retrieve student: " + ex.getMessage();
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", errorMessage);  // Return as JSON
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse.toString());
    }



}