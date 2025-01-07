package com.school.service;


import com.school.exception.ClassAlreadyExistsException;
import com.school.exception.ClasssNotFoundException;
import com.school.exception.StudentNotFoundException;
import com.school.model.SchoolClass;
import com.school.model.Student;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SchoolService {

    private Map<String, SchoolClass> classMap = new HashMap<>();


    public List<SchoolClass> getAllClasses() {
        return new ArrayList<>(classMap.values());
    }


    public void addClass(SchoolClass schoolClass) {
        if (classMap.containsKey(schoolClass.getClassName())) {
            throw new ClassAlreadyExistsException("Class already exists.");
        }
        classMap.put(schoolClass.getClassName(), schoolClass);
    }


    public SchoolClass getClassByName(String className) throws ClasssNotFoundException {
        if (!classMap.containsKey(className)) {
            throw new ClasssNotFoundException("Class not found.");
        }
        return classMap.get(className);
    }


    public void deleteClass(String className) throws ClasssNotFoundException {
        if (!classMap.containsKey(className)) {
            throw new ClasssNotFoundException("Class not found.");
        }
        classMap.remove(className);
    }


    public void addStudentToClass(String className, Student student) throws ClasssNotFoundException {
        SchoolClass schoolClass = getClassByName(className);
        schoolClass.getStudents().add(student);
    }


    public void updateStudentDetails(String className, String rollNo, Student updatedStudent) throws ClasssNotFoundException {
        SchoolClass schoolClass = getClassByName(className);
        List<Student> students = schoolClass.getStudents();
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getRollNo().equals(rollNo)) {
                students.set(i, updatedStudent);
                return;
            }
        }
        throw new StudentNotFoundException("Student not found.");
    }


    public void deleteStudentFromClass(String className, String rollNo) throws ClasssNotFoundException {
        SchoolClass schoolClass = getClassByName(className);
        schoolClass.getStudents().removeIf(student -> student.getRollNo().equals(rollNo));
    }


    public List<Student> getStudentsBySubject(String subject) {
        List<Student> result = new ArrayList<>();
        for (SchoolClass schoolClass : classMap.values()) {
            for (Student student : schoolClass.getStudents()) {
                if (subject == null || student.getSubjects().contains(subject)) {
                    result.add(student);
                }
            }
        }
        return result;
    }


    public List<Student> getStudentsByBirthYear(String year) {
        List<Student> result = new ArrayList<>();
        for (SchoolClass schoolClass : classMap.values()) {
            for (Student student : schoolClass.getStudents()) {
                if (student.getDateOfBirth().endsWith(year)) {
                    result.add(student);
                }
            }
        }
        return result;
    }
}
