package com.xyz.schoolManagementSystem.model;

import java.util.List;

public class Classs {
    private String className;
    private String roomNo;
    private List<Student> students;

    // Getters and Setters


    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "className :" + className + '\'' +
                ", roomNo : " + roomNo + '\'';
    }
}
