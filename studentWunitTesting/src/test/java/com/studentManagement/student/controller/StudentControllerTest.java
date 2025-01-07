package com.studentManagement.student.controller;

import com.studentManagement.student.entity.Student;
import com.studentManagement.student.exception.ApplicationException;
import com.studentManagement.student.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private MockMvc mockMvc;

    private Student student;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();

        student = new Student();
        student.setStudentId(1);
        student.setStudentname("John Doe");
        student.setCity("New York");
        student.setCgpa(9.5);
        student.setCourse_fees(1000.0);
    }

    @Test
    public void testAddStudentSuccess() throws Exception {
        // Create a mock Student object to be returned by the addStudent method
        Student studentToReturn = new Student();
        studentToReturn.setStudentId(1);
        studentToReturn.setStudentname("John Doe");
        studentToReturn.setCity("New York");
        studentToReturn.setCgpa(9.5);
        studentToReturn.setCourse_fees(1000.0);

        // Mock the behavior of the addStudent() method to return the student object
        when(studentService.addStudent(any(Student.class))).thenReturn(studentToReturn);

        // Perform the POST request
        mockMvc.perform(post("/add")
                        .contentType("application/json")
                        .content("{\"studentId\": 1, \"studentname\": \"John Doe\", \"city\": \"New York\", \"cgpa\": 9.5, \"course_fees\": 1000.0}"))
                .andExpect(status().isCreated())  // Expect 201 Created response
                .andExpect(content().string("Student added Successfully"));  // Expect success message

        // Verify that the addStudent method was called exactly once
        verify(studentService, times(1)).addStudent(any(Student.class));
    }



    @Test
    public void testAddStudentFailure() throws Exception {
        // Simulating an exception in service layer
        doThrow(new ApplicationException("Service error")).when(studentService).addStudent(any(Student.class));

        mockMvc.perform(post("/add")
                        .contentType("application/json")
                        .content("{\"studentId\": 1, \"studentname\": \"John Doe\", \"city\": \"New York\", \"cgpa\": 9.5, \"course_fees\": 1000.0}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failed to add student"));

        verify(studentService, times(1)).addStudent(any(Student.class));
    }

    @Test
    public void testGetAllStudents() throws Exception {
        List<Student> students = Collections.singletonList(student);
        when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(get("/view"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].studentId").value(1))
                .andExpect(jsonPath("$[0].studentname").value("John Doe"));

        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    public void testGetAllStudentsNoContent() throws Exception {
        when(studentService.getAllStudents()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/view"))
                .andExpect(status().isNoContent());

        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    public void testGetStudentByIdSuccess() throws Exception {
        Optional<Student> optionalStudent = Optional.of(student);
        when(studentService.getStudentById(1)).thenReturn(optionalStudent);

        mockMvc.perform(get("/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value(1))
                .andExpect(jsonPath("$.studentname").value("John Doe"));

        verify(studentService, times(1)).getStudentById(1);
    }

    @Test
    public void testGetStudentByIdNotFound() throws Exception {
        when(studentService.getStudentById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Student not found"));

        verify(studentService, times(1)).getStudentById(1);
    }

    @Test
    public void testGetStudentByIdError() throws Exception {
        when(studentService.getStudentById(1)).thenThrow(new ApplicationException("Error fetching student"));

        mockMvc.perform(get("/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Failed to retrieve student: Error fetching student"));

        verify(studentService, times(1)).getStudentById(1);
    }

    @Test
    public void testGetStudentsByCityAndCgpa() throws Exception {
        List<Student> students = Collections.singletonList(student);
        when(studentService.getStudentsByCityAndCgpa("New York", 9.0)).thenReturn(students);

        mockMvc.perform(get("/city-cgpa")
                        .param("city", "New York")
                        .param("cgpa", "9.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].studentId").value(1))
                .andExpect(jsonPath("$[0].studentname").value("John Doe"));

        verify(studentService, times(1)).getStudentsByCityAndCgpa("New York", 9.0);
    }

    @Test
    public void testGetStudentsByCityAndCgpaNoContent() throws Exception {
        when(studentService.getStudentsByCityAndCgpa("New York", 9.0)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/city-cgpa")
                        .param("city", "New York")
                        .param("cgpa", "9.0"))
                .andExpect(status().isNoContent());

        verify(studentService, times(1)).getStudentsByCityAndCgpa("New York", 9.0);
    }

    @Test
    public void testReduceFeesForTopStudents() throws Exception {
        doNothing().when(studentService).reduceFeesForTopStudents();

        mockMvc.perform(put("/reduce-fees"))
                .andExpect(status().isOk())
                .andExpect(content().string("Fees reduced for students with CGPA = 10"));

        verify(studentService, times(1)).reduceFeesForTopStudents();
    }

    @Test
    public void testDeleteStudent() throws Exception {
        doNothing().when(studentService).deleteStudentById(1);

        mockMvc.perform(delete("/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted successfully"));

        verify(studentService, times(1)).deleteStudentById(1);
    }
}
