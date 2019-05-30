package com.example.pieperj.powerschool;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Student {

    private String name, objectId;

    private int year, daysAttended, daysTotal;

    private double attendence, grade;
    private List<Assignment> assignments;

    public Student(String name, int year) {
        this.name = name;
        this.year = year;
        this.attendence = 100;
        this.grade = 100;
        daysAttended = 0;
        daysTotal = 0;
        assignments = new ArrayList<>();
    }

    private int getTotalPointsPossible() {
        int total = 0;
        for(Assignment a : assignments) {
            total += a.getPointsTotal();
        }
        return total;
    }

    private int getTotalPointsEarned() {
        int total = 0;
        for(Assignment a : assignments) {
            total += a.getPointsEarned();
        }
        return total;
    }

    public void calcTotalGrade() {

        int totalAssignments = 0;
        double scores = 0;

        for(int i = 0; i < assignments.size(); i++) {
            totalAssignments++;
            int pointsPossible = assignments.get(i).getPointsTotal();
            int pointsEarned = assignments.get(i).getPointsEarned();

            double score = (double)pointsEarned / (double)pointsPossible;
            scores += score;

        }

        this.grade = scores / (double)totalAssignments;

    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getDaysTotal() {
        return daysTotal;
    }

    public void setDaysTotal(int daysTotal) {
        this.daysTotal = daysTotal;
    }

    public void addDayTotal() {
        this.daysTotal++;
    }

    public Student() {
        this.name = "";
        this.year = 9;
        this.attendence = 100.00;
        this.grade  = 100.00;
        daysAttended = 0;
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

    public int getDaysAttended() {
        return daysAttended;
    }

    public void setDaysAttended(int days) {
        this.daysAttended = days;
    }

    public void addDayAttended() {
        daysAttended++;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }


    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }
    /*
    public String toString() {
        return String.format("%s, Year %d - %.2f | Grade: %f", name, year, attendence, grade);
    }
    */

    public String toString(){
        return String.format(Locale.US, "%s : %d / %d", name, getTotalPointsEarned(), getTotalPointsPossible());
    }

}
