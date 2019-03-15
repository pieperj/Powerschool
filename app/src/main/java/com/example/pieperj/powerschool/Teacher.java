package com.example.pieperj.powerschool;

import java.util.ArrayList;

public class Teacher {

    private String name;
    private ArrayList<Student> classroom;

    public Teacher(ArrayList<Student> classroom) {
        this.classroom = classroom;
    }

    public Teacher() {
        this.classroom = new ArrayList<>();
    }

    public ArrayList<Student> getClassroom() {
        return classroom;
    }

    public void setClassroom(ArrayList<Student> classroom) {
        this.classroom = classroom;
    }

    public String toString() {
        return "" + name + " - " + classroom;
    }
}
