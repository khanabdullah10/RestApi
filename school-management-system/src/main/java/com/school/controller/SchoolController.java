package com.school.controller; // controller/SchoolController.java

import com.school.exception.ClasssNotFoundException; // Fixed typo
import com.school.model.SchoolClass;
import com.school.model.Student;
import com.school.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/school")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @GetMapping("/getClasses")
    public ResponseEntity<List<SchoolClass>> getAllClasses() {
        List<SchoolClass> classes = schoolService.getAllClasses();
        return ResponseEntity.ok(classes);
    }

    @PostMapping("/addClass")
    public ResponseEntity<String> addClass(@RequestBody SchoolClass schoolClass) {
        schoolService.addClass(schoolClass);
        return ResponseEntity.ok("Added successfully");
    }

    @GetMapping("/class/{name}")
    public ResponseEntity<SchoolClass> getClassByName(@PathVariable String name) throws ClasssNotFoundException {
        SchoolClass schoolClass = schoolService.getClassByName(name);
        return ResponseEntity.ok(schoolClass);
    }

    @DeleteMapping("/class/{name}")
    public ResponseEntity<String> deleteClass(@PathVariable String name) throws ClasssNotFoundException {
        schoolService.deleteClass(name);
        return ResponseEntity.ok("Deleted successfully");
    }

    @PostMapping("/class/{name}/student")
    public ResponseEntity<String> addStudentToClass(@PathVariable String name, @RequestBody Student student) throws ClasssNotFoundException {
        schoolService.addStudentToClass(name, student);
        return ResponseEntity.ok("Student added to the class successfully");
    }

    @PutMapping("/class/{className}/student/{rollNo}")
    public ResponseEntity<String> updateStudent(@PathVariable String className, @PathVariable String rollNo, @RequestBody Student student) throws ClasssNotFoundException {
        schoolService.updateStudentDetails(className, rollNo, student);
        return ResponseEntity.ok("Updated successfully");
    }

    @DeleteMapping("/class/{className}/student/{rollNo}")
    public ResponseEntity<String> deleteStudent(@PathVariable String className, @PathVariable String rollNo) throws ClasssNotFoundException {
        schoolService.deleteStudentFromClass(className, rollNo);
        return ResponseEntity.ok("Student deleted successfully");
    }

    @GetMapping("/students/subject")
    public ResponseEntity<List<Student>> getStudentsBySubject(@RequestParam(required = false) String subject) {
        List<Student> students = schoolService.getStudentsBySubject(subject);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/students/year/{year}")
    public ResponseEntity<List<Student>> getStudentsByBirthYear(@PathVariable String year) {
        List<Student> students = schoolService.getStudentsByBirthYear(year);
        return ResponseEntity.ok(students);
    }
}