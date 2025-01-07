package com.xyz.schoolManagementSystem.repository;

import com.xyz.schoolManagementSystem.model.Classs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassRepository {
    private Map<String, Classs> classes;

    public ClassRepository() {
        this.classes = new HashMap<>();
    }

    public void addClass(Classs clazz) {
        classes.put(clazz.getClassName(), clazz);
    }

    public Classs getClass(String className) {
        return classes.get(className);
    }

    public void deleteClass(String className) {
        classes.remove(className);
    }


    public List<Classs> getAllClasses() {
        return new ArrayList<>(classes.values());
    }

}