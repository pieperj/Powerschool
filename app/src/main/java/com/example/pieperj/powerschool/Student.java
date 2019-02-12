package com.example.pieperj.powerschool;

import java.util.ArrayList;
import java.util.List;

public class Student {

    private String name;
    private int year, daysMissed;
    private double attendence, grade;
    private List<Assignment> assignments;

    public Student(String name, int year) {
        this.name = name;
        this.year = year;
        this.attendence = 100;
        this.grade = 100;
        daysMissed = 0;
        assignments = new ArrayList<>();
    }

    public Student() {
        this.name = "";
        this.year = 9;
        this.attendence = 100.00;
        this.grade  = 100.00;
        daysMissed = 0;
        assignments = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getAttendence() {
        return attendence;
    }

    public void setAttendence(double attendence) {

        this.attendence = attendence;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }


    public int getDaysMissed() {
        return daysMissed;
    }

    public void setDaysMissed(int days) {
        this.daysMissed = days;
    }

    public void addDayMissed() {
        daysMissed++;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }


    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public String toString() {
        return String.format("%s, Year %d - %.2f | Grade: %f", name, year, attendence, grade);
    }

}
