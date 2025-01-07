package com.studentManagement.student.studentRepository;

import com.studentManagement.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByCityAndCgpaGreaterThan(String city, Double cgpa);

    Optional<Student> findById(Integer id);

}
