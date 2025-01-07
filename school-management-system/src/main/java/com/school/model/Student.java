package com.school.model;

import lombok.*;
import org.springframework.stereotype.Controller;

import java.util.List;


// model/Student.java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class  Student {

    private String rollNo;
    private String name;
    private String dateOfBirth; // Format DD-MM-YYYY
    private List<String> subjects;

    // Constructors, Getters, Setters
}



