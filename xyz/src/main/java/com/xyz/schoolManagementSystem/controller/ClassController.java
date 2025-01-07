package com.xyz.schoolManagementSystem.controller;

import com.xyz.schoolManagementSystem.model.Classs;
import com.xyz.schoolManagementSystem.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClassController {
    @Autowired
    private ClassService classService;

    @GetMapping
    public ResponseEntity<List<Classs>> getAllClasses() {
        List<Classs> classes = classService.getAllClasses();
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Classs> addClass(@RequestBody Classs clazz) {
        Classs createdClass = classService.addClass(clazz);
        return new ResponseEntity<>(createdClass, HttpStatus.CREATED);
    }

    @GetMapping("/{className}")
    public ResponseEntity<Classs> getClassByName(@PathVariable String className) throws ClassNotFoundException {
        Classs clazz = classService.getClassByName(className);
        return new ResponseEntity<>(clazz, HttpStatus.OK);
    }

    @DeleteMapping("/{className}")
    public ResponseEntity<Void> deleteClass(@PathVariable String className) {
        classService.deleteClass(className);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}