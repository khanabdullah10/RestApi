package com.school.model;

import lombok.*;

import java.util.List;

// model/SchoolClass.java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SchoolClass {
    private String className;
    private List<Student> students;

}
