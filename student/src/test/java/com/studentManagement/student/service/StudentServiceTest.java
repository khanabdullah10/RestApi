package com.studentManagement.student.service;

import com.studentManagement.student.entity.Student;
import com.studentManagement.student.exception.ApplicationException;
import com.studentManagement.student.studentRepository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddStudent() {
        Student student = new Student(1, "John Doe", "123 Street", "City", 9.5, 15000.0);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student result = studentService.addStudent(student);
        assertNotNull(result);
        assertEquals("John Doe", result.getStudentname());
    }

    @Test
    void testAddStudent_ExceptionHandling() {
        Student student = new Student(1, "John Doe", "123 Street", "City", 9.5, 15000.0);
        when(studentRepository.save(any(Student.class))).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(ApplicationException.class, () -> {
            studentService.addStudent(student);
        });
        assertEquals("Failed to add student: Database error", exception.getMessage());
    }

    @Test
    void testGetAllStudents() {
        List<Student> students = Arrays.asList(
                new Student(1, "John Doe", "123 Street", "City", 9.5, 15000.0),
                new Student(2, "Jane Smith", "456 Avenue", "City", 8.0, 12000.0)
        );
        when(studentRepository.findAll()).thenReturn(students);

        List<Student> result = studentService.getAllStudents();
        assertEquals(2, result.size());
    }

    @Test
    void testGetAllStudents_EmptyList() {
        when(studentRepository.findAll()).thenReturn(Collections.emptyList());

        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            studentService.getAllStudents();
        });
        assertEquals("Failed to retrieve students: No students found in the database.", exception.getMessage());
    }

    @Test
    void testGetStudentById() {
        Student student = new Student(1, "John Doe", "123 Street", "City", 9.5, 15000.0);
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));

        Optional<Student> result = studentService.getStudentById(1);
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getStudentname());
    }

    @Test
    void testGetStudentById_NotFound() {
        when(studentRepository.findById(999)).thenReturn(Optional.empty());

        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            studentService.getStudentById(999);
        });
        assertEquals("Failed to retrieve student: Student with ID 999 not found.", exception.getMessage());
    }

    @Test
    void testGetStudentsByCityAndCgpa() {
        List<Student> students = Arrays.asList(
                new Student(1, "John Doe", "Bandra", "City", 9.5, 15000.0),
                new Student(2, "Jane Smith", "Bandra", "City", 8.0, 12000.0)
        );
        when(studentRepository.findByCityAndCgpaGreaterThan("City", 8.0)).thenReturn(students);

        List<Student> result = studentService.getStudentsByCityAndCgpa("City", 8.0);
        assertEquals(2, result.size());
    }

    @Test
    void testGetStudentsByCityAndCgpa_EmptyResult() {
        when(studentRepository.findByCityAndCgpaGreaterThan("City", 10.0)).thenReturn(Collections.emptyList());

        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            studentService.getStudentsByCityAndCgpa("City", 10.0);
        });
        assertEquals("Failed to retrieve students: No students found in City with CGPA greater than 10.0", exception.getMessage());
    }

    @Test
    void testReduceFeesForTopStudents() {
        Student student = new Student(1, "John Doe", "123 Street", "City", 10.0, 15000.0);
        when(studentRepository.findAll()).thenReturn(Collections.singletonList(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        studentService.reduceFeesForTopStudents();
        assertEquals(7500.0, student.getCourse_fees());
    }

    @Test
    void testReduceFeesForTopStudents_NoTopStudents() {
        Student student = new Student(1, "John Doe", "123 Street", "City", 8.0, 15000.0);
        when(studentRepository.findAll()).thenReturn(Collections.singletonList(student));

        studentService.reduceFeesForTopStudents();
        assertEquals(15000.0, student.getCourse_fees());  // Fees should not change for non-top students
    }

    @Test
    void testDeleteStudentById() {
        doNothing().when(studentRepository).deleteById(1);

        studentService.deleteStudentById(1);
        verify(studentRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteStudentById_NotFound() {
        doThrow(new EmptyResultDataAccessException("Student not found", 1)).when(studentRepository).deleteById(1);

        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            studentService.deleteStudentById(1);
        });
        assertEquals("Student with ID 1 not found for deletion.", exception.getMessage());
    }

    // Additional edge cases for coverage:

    @Test
    void testGetStudentById_ThrowsException() {
        when(studentRepository.findById(anyInt())).thenThrow(new RuntimeException("Unexpected error"));

        Exception exception = assertThrows(ApplicationException.class, () -> {
            studentService.getStudentById(1);
        });
        assertTrue(exception.getMessage().contains("Failed to retrieve student: Unexpected error"));
    }

    @Test
    void testAddStudent_ThrowsException() {
        when(studentRepository.save(any(Student.class))).thenThrow(new RuntimeException("Database issue"));

        Exception exception = assertThrows(ApplicationException.class, () -> {
            studentService.addStudent(new Student());
        });
        assertEquals("Failed to add student: Database issue", exception.getMessage());
    }

    @Test
    void testDeleteStudentById_ThrowsException() {
        doThrow(new RuntimeException("Unexpected error")).when(studentRepository).deleteById(anyInt());

        Exception exception = assertThrows(ApplicationException.class, () -> {
            studentService.deleteStudentById(1);
        });
        assertEquals("Failed to delete student: Unexpected error", exception.getMessage());
    }

}
